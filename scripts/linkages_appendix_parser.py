"""script that will intrepret and load database with definitions found in the appendixes
of the linkages book."""

import re
import pyodbc

def process_file(file_name):
    nics = []
    lines = open(file_name).readlines()
    for line in lines:
        line = line.rstrip("\n")
        vals = re.search("^([0-9]*)\t([\S ]*)\t([\S ]*)", line)
        if vals:
            (code, name, description) = map(lambda x: vals.group(x), range(1,4))
            if code != '':
                nics.append([code, name, description])
            else:
                nics[-1][1] += name
                nics[-1][2] += description
        else:
            nics[-1][2] += line
    return nics

def dump_to_table(table_name, codes):
    conn = pyodbc.connect("DSN=bigstreet;Database=bigstreet")
    crsr = conn.cursor()
    for code_tuple in codes:
        print code_tuple
        sql = "INSERT INTO [dbo].[%s](code, name, definition) VALUES(?,?,?)" % table_name
        crsr.execute(sql, *code_tuple)
    conn.commit()

def main():
    nics = process_file(r"parsable/nic_codes.txt")
    dump_to_table("linkagesbook_nic_codes", nics)
    nocs = process_file(r"parsable/noc_codes.txt")
    dump_to_table("linkagesbook_noc_codes", nocs)

if __name__ == '__main__':
    main()
