print("RELIC JSON MAKER")
f = open("newText.txt", "w")
while 1:
    print("\nENTER :")
    x = input()
    if x == "q":
        break
    f.write("  \"rorgmod:"+x+"\": {\n    \"NAME\": \""+x+"\",\n    \"FLAVOR\": \"\",\n    \"DESCRIPTIONS\": [\n      \"\"\n ]\n  },")
f.close()