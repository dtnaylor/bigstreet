"""Original script that parses and intreprets the nursing linkages book
and exports into either XML or database format."""

import re
import pyodbc
import xml.dom.minidom

class NursingDiagnosis(object):
    def __init__(self, name, definition):
        self.name = name
        self.definition = definition 
        self.outcomes = []

    def __repr__(self):
        return "<%s ('%s')" % (self.__class__.__name__, self.name)

class NursingOutcome(object):
    def __init__(self, name):
        self.name = name
        self.major_interventions = []
        self.suggested_interventions = []
        self.optional_interventions = []
        self.definition = []

    def __repr__(self):
        return "<%s ('%s')" % (self.__class__.__name__, self.name)

def process_chapter(lines):
    # Attempt to find all of the Nursing Diagnoses 
    diagnoses_starts = filter(lambda i: lines[i].startswith("NURSING DIAGNOSIS"), range(0,len(lines)))
    # find the locations of diags and then zip in the ends of the of the next diag to find a block
    diagnoses_blocks = zip(diagnoses_starts, map(lambda x: x-1, diagnoses_starts[1:] + [len(lines)]))
    # return list of processed diganosis objects
    return map(lambda (s,e): process_diagnosis_block(lines[s:e]), diagnoses_blocks)

def process_diagnosis_block(lines):
    name = re.search("^NURSING DIAGNOSIS: (\w.*)" , lines[0])
    # Definitions can span multiple lines (it happens once... <sigh>)
    def_lines = ""
    i = 1
    while not re.search("^\s*Major\s*Suggested\s*Optional", lines[i]):
        def_lines += lines[i].rstrip()
        i += 1
    definition = re.search("^DEFINITION: (\w.*)" , def_lines)
    assert all((name, definition))

    # each nursing diagnosis can contain have multiple outcomes
    # and each outcome can have multiple "major interventions",
    # "suggested interventions", and "optional interventions"
    # that can be used to reach the particular outcome.
    # we need to find all of the outcomes. These aren't necessarily
    # easy to detect since they fall under the same syntax as the 
    # interventions from the previous outcome. Therefore, we have to
    # search for "DEFINITION:" which is found below each outcome
    # and then look at the terms on the lines previous. 

    nd = NursingDiagnosis(name=name.group(1), definition=definition.group(1))
    outcome_def_lines = []
    for j in range(i, len(lines)-1):
        if lines[j].startswith("DEFINITION:"):
            outcome_def_lines.append(j)
    
    # try to figure out title of outcome
    outcome_title_lines = []
    outcome_titles = []
    for def_start in outcome_def_lines:
        outcome = ""
        i = def_start - 1
        while not re.search("^\s|Outcome",lines[i]):
            outcome = lines[i].rstrip("\n") + outcome
            i -= 1
        outcome_title_lines.append(i)
        outcome_titles.append(outcome)
    outcome_blocks = zip(outcome_titles, outcome_def_lines, outcome_title_lines[1:] + [len(lines)-1])
    for name, start_line, end_line in outcome_blocks:
        if re.search("^Major\s*Suggested\s*Optional", lines[end_line-1]):
            end_line = end_line - 1
        outcome = process_outcome_block(name, lines[start_line:end_line])
        nd.outcomes.append(outcome)
    return nd

def process_outcome_block(name, lines):
    assert lines[0].startswith("DEFINITION:")
    outcome = NursingOutcome(name)
    inter_types = ("major","suggested","optional")
    for line in lines:
        comps = line.rstrip("\n").split("\t")
        # handle definitions...
        if comps[0].startswith("DEFINITION:"):
            outcome.definition = comps[0][12:]
        elif comps[0] and comps[0][0] == " ":
            outcome.definition += comps[0]
        # handle interventions
        for i, itype in (zip(range(1,4), inter_types)):
            i_attr = getattr(outcome, "%s_interventions" % itype)
            if len(comps) > i and len(comps[i]) > 0:
                if comps[i].startswith(' '):
                    i_attr[-1] += comps[i]
                else:
                    i_attr.append(comps[i])
    # sometimes there are still some bizarre extra spaces...
    for inter_type in inter_types:
        setattr(outcome,"%s_interventions" % inter_type,
            map(lambda x: x.rstrip().replace('  ',' '),
                getattr(outcome,"%s_interventions" % inter_type)))
    outcome.definition = outcome.definition.replace('  ',' ')
    #print outcome.name 
    #print "\t", outcome.major_interventions
    #print "\t", outcome.suggested_interventions
    #print "\t", outcome.optional_interventions
    return outcome

def dump_to_xml(output_path, diagnoses):
    doc = xml.dom.minidom.Document()
    nnn = doc.createElement("nandanicnoc")
    doc.appendChild(nnn)
    diags_node = doc.createElement("diagnoses")
    nnn.appendChild(diags_node)
    for diagnosis in diagnoses:
        diag_node = doc.createElement("diagnosis")
        diags_node.appendChild(diag_node)
        diag_node_name = doc.createElement("name")
        diag_node_name.appendChild(doc.createTextNode(diagnosis.name))
        diag_node.appendChild(diag_node_name)
        diag_node_definition = doc.createElement("definition")
        diag_node_definition.appendChild(doc.createTextNode(diagnosis.definition))
        diag_node.appendChild(diag_node_definition)
        outcomes_node = doc.createElement("outcomes")
        diag_node.appendChild(outcomes_node)
        for outcome in diagnosis.outcomes:
            outcome_node = doc.createElement("outcome")
            outcomes_node.appendChild(outcome_node)
            outcome_node_name = doc.createElement("name")
            outcome_node_name.appendChild(doc.createTextNode(outcome.name))
            outcome_node.appendChild(outcome_node_name)
            outcome_node_definition = doc.createElement("definition")
            outcome_node_definition.appendChild(doc.createTextNode(outcome.definition))
            outcome_node.appendChild(outcome_node_definition)
            for intervention_type in ('major','suggested','optional'):
                name = "%s_interventions" % intervention_type
                interventions = getattr(outcome, name)
                interventions_node = doc.createElement(name)
                outcome_node.appendChild(interventions_node)
                for intervention in interventions:
                    intervention_node = doc.createElement("intervention")
                    intervention_node.appendChild(doc.createTextNode(intervention))
                    interventions_node.appendChild(intervention_node)
    doc.writexml(file(output_path, 'w'), indent="", addindent="  ", newl="\n")  

def dump_to_database(diagnoses):
    conn = pyodbc.connect("DSN=bigstreet;Database=bigstreet")
    crsr = conn.cursor()
    for diagnosis in diagnoses:
        diagnosis_id = crsr.execute("INSERT INTO [dbo].[linkagesbook_diagnoses](name, definition) VALUES(?,?); SELECT SCOPE_IDENTITY()",
            diagnosis.name, diagnosis.definition).fetchone()[0]
        for outcome in diagnosis.outcomes:
            outcome_id = crsr.execute("INSERT INTO [dbo].[linkagesbook_outcomes](diagnosis_id, name, definition) VALUES(?,?,?); SELECT SCOPE_IDENTITY()",
                diagnosis_id, outcome.name, outcome.definition).fetchone()[0]
            for intervention_type in ('major','suggested','optional'):
                name = "%s_interventions" % intervention_type
                interventions = getattr(outcome, name)
                for intervention in interventions:
                    crsr.execute("INSERT INTO [linkagesbook_interventions](outcome_id, name, type) VALUES(?,?,?)",
                        outcome_id, intervention, intervention_type)
    conn.commit()


def main():
    # process text files into native python objects
    diagnoses = []
    for i in range(5,9):
        print ("processing chapter %i..." % i),
        diagnoses += process_chapter(open(r"parsable/chapter%i.txt" % i).readlines())
        print "ok"
    # dump to xml 
    #dump_to_xml("diagnoses.xml", diagnoses)
    dump_to_database(diagnoses)

if __name__ == "__main__":
    main()

