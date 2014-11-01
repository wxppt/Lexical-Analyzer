DATATYPE:1======\ (int|double|float|char|boolean|double|float|int|short|long)\ 
RESERVED:1======\ (abstract|assert|break|byte|case|catch|class|const|continue|def|ult|do|else|enum|extends|final|finally|for|goto|if|implement|import|instanceof|interface|native|new|package|private|protected|public|return|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\ 
ID:0======(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z)(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|0|1|2|3|4|5|6|7|8|9|_|$)*
SYMBOL:1======:|\.|<|>|\(|\)|[|]|{|}
SEPERATOR:1======,|;
OP:0======+|-|\*|/|%|\||&|!|=
H_OP:1======++|--|==|+=|-=|&&|\|\|
STRING:1======"(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|0|1|2|3|4|5|6|7|8|9)*"
CHAR:1======'(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|0|1|2|3|4|5|6|7|8|9)'
INTEGER:1======0|1|2|3|4|5|6|7|8|9|(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*
FLOAT:1======0|1|2|3|4|5|6|7|8|9|(1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*.(0|1|2|3|4|5|6|7|8|9)*
BOOL:1======true|false
BLANK:0======(\ )