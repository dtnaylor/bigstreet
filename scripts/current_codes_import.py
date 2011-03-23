import pyodbc

conn = pyodbc.connect("DSN=bigstreet;Database=bigstreet")
crsr = conn.cursor()

with open("noc_codes_parsable.txt") as f:
   for l in f.read().split('\r'):
       crsr.execute("INSERT INTO [dbo].[noc_codes](code, name) VALUES(?,?)", *l.split(" ", 1))

with open("nic_codes_parsable.txt") as f:
   for l in f.read().split('\n'):
       crsr.execute("INSERT INTO [dbo].[nic_codes](code, name) VALUES(?,?)", *l.split("  ", 1))

with open("nanda_codes_parsable.txt") as f:
   for l in f.read().split('\n'):
       crsr.execute("INSERT INTO [dbo].[nanda_codes](code, name) VALUES(?,?)", *l.split("\t", 1))

conn.commit()
