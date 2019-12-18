problem II (Cross-sectional nodes)the file (CrossSecNode.java): 
Since users and/or groups represent nodes in our graph, so that, start to enter the user's information: 
the user's last name, user's first name, user's age, user's gender, primary language, SSN login id, email, and group's name. 
Nodes represented in characters: a, b, c, d, e, f, g, h, i, through j , then enter the source and destination nodes to make a search throughout a graph
and find traversals of the set (non-cross-sectional) nodes who contained between two square parentheses [Non-Cross-Sectional nodes] and
a set of cross-sectional nodes that are shown outside the square parentheses that represents the cross-edges whose got nodes that do not have ancestor
and a descendant relationship between them by using DFS. In other words that is DFS method recognizes the cross-sectional nodes by using its traversal results
that all nodes are finished once all their children are finished as well.

Inputs and results Test:
-----------------------------
Enter a User Last Name: ali
Enter a User First Name: fadhil
Enter a User Age: 49
Enter a User Gender: male
Enter a User Primary Language: english
Enter a User SSN: 12345
Enter a User Email: fadhil.ali@okstate.edu
Enter a Group: G313
-----------------------------
Select Nodes from(a,b,c,...j):
-----------------------------
Enter a source node: b
Enter a destination node: h
------------------------------
Registered Nodes(a,b,c,...j):
--------------------------------
[Node]: (a) Is Connected to Attributes ->> c b
[Node]: (b) Is Connected to Attributes ->> e d
[Node]: (c) Is Connected to Attributes ->> d
[Node]: (d) Is Connected to Attributes ->> e
[Node]: (e) Is Connected to Attributes ->>
[Node]: (f) Is Connected to Attributes ->> g
[Node]: (g) Is Connected to Attributes ->> e
[Node]: (h) Is Connected to Attributes ->>
[Node]: (i) Is Connected to Attributes ->>
[Node]: (j) Is Connected to Attributes ->>

(Non Cross-Sectional Nodes) DFS/BFS Traversal Trace:
a c d e b h
----------------------------------------------------------------------------
Registered[Node] = (a) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [c, b]
----------------------------------------------------------------------------
Registered[Node] = (b) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [h, e, d]
----------------------------------------------------------------------------
Registered[Node] = (c) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [d]
----------------------------------------------------------------------------
Registered[Node] = (d) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [e]
----------------------------------------------------------------------------
Registered[Node] = (e) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [] f g
----------------------------------------------------------------------------
Registered[Node] = (f) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [g]
----------------------------------------------------------------------------
Registered[Node] = (g) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [e]
----------------------------------------------------------------------------
Registered[Node] = (h) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [] i
----------------------------------------------------------------------------
Registered[Node] = (i) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = [] j
----------------------------------------------------------------------------
Registered[Node] = (j) -->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = []

Clearly, once I chose the source and destination nodes, the DFS traversal assign and select the path.
In this inputs test, the nodes (f,g, i, and j) are cross-sectional nodes, while (c,b,h,e, and d) are non-cross-sectional nodes.

Thanks!
Fadhil