import datatransporttools
import datatransporttools.csv_
import datatransporttools.mssql
    
input_ = datatransporttools.csv_.CSVFile(
    file_name = 'output4nick.txt')
    
output_ = datatransporttools.mssql.MSSQLTable('Driver={SQL Native Client};Server=itsnt286;Database=bigstreet;Trusted_Connection=Yes;','two_item_correlations')
    
datatransporttools.transfer(
    inputobj = input_,
    outputobj = output_,
    include_headers = False)