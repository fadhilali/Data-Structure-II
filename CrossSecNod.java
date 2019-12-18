//====================================================================================================================================================
// CS5413 : pgm2 - problem II written by Ali Alsahlanee, Fadhil (PART 2)
// Semester : Fall 2019
// Prof. N.Park
// Three programs: Assignment Identity Management, Set of Cross-Sectional Nodes
// Description:
// This code will enter the Users and Groups then connected nodes as listed:from a,b,c,d,e,f,g,h,i to j represents Users/Groups list
// First of all, enter the source and destination to make a trusted list of the Registerd Nodes
// associated with the connected edges represents the Attributes; however, according to the DFS/BFS travesals that give Non Cross-Sectional Nodes
// applying these conditions by entering the source and destination to find the registered graph and shown the set of cross-sectional nodes  
//=====================================================================================================================================================
import java.util.*;
import java.io.*;
@SuppressWarnings("unchecked")
public class CrossSecNod {

    public static void main(String[] args)throws Exception {
       ArrayList<String> PrivNodeGraphyUser = new ArrayList<String>(100);// this Arraylist for holding the users nodes
	   ArrayList<String> PrivNodeGraphyGroup = new ArrayList<String>(100);// this Arraylist fro holding the groups nodes
		// Users/Groups (Nodes)
		PrivNodeGraphyUser.add("a");// the (initialized) Node as root for the users/groups
        PrivNodeGraphyUser.add("b");
        PrivNodeGraphyUser.add("c");
        PrivNodeGraphyUser.add("d");
        PrivNodeGraphyUser.add("e");
        PrivNodeGraphyUser.add("f");
        PrivNodeGraphyUser.add("g");
		PrivNodeGraphyUser.add("h");
		PrivNodeGraphyUser.add("i");
		PrivNodeGraphyUser.add("j");
		//Groups
		/*PrivNodeGraphyGroup.add("1");
        PrivNodeGraphyGroup.add("2");
        PrivNodeGraphyGroup.add("3");
        PrivNodeGraphyGroup.add("4");
        PrivNodeGraphyGroup.add("5");
        PrivNodeGraphyGroup.add("6");
        PrivNodeGraphyGroup.add("7");
		PrivNodeGraphyGroup.add("8");
		PrivNodeGraphyGroup.add("9");
		PrivNodeGraphyGroup.add("10");*/
		
		try{
			Scanner scinputes = new Scanner(System.in);
			System.out.println("-----------------------------");
			// input user's attributes
			System.out.print("Enter a User Last Name: "); 
			String u = scinputes.nextLine();
			System.out.print("Enter a User First Name: "); 
			String uu = scinputes.nextLine();
			System.out.print("Enter a User Age: "); 
			String ag = scinputes.nextLine();
			System.out.print("Enter a User Gender: "); 
			String gend = scinputes.nextLine();
			System.out.print("Enter a User Primary Language: "); 
			String lagn = scinputes.nextLine();
			System.out.print("Enter a User SSN: "); 
			String ssn = scinputes.nextLine();
			System.out.print("Enter a User Email: "); 
			String email = scinputes.nextLine();
			// input group
			System.out.print("Enter a Group: ");
			String gp = scinputes.nextLine(); // input groups
			System.out.println("-----------------------------");
			System.out.println("Select Nodes from(a,b,c,...j):");
			System.out.println("-----------------------------");
			System.out.print("Enter a source node: ");
			String p = scinputes.nextLine();
			System.out.print("Enter a destination node: ");
			String q = scinputes.nextLine();
			System.out.println("------------------------------");
		
			
			
		//PrivNodeGraphyGroup.remove(p);
		//PrivNodeGraphyUser.add(p);
		//PrivNodeGraphyUser.remove(p);
		PrivGraphy privgraphy = new PrivGraphy(PrivNodeGraphyUser);
        privgraphy.PrivGaddEdg("a", "b");
        privgraphy.PrivGaddEdg("a", "c");
        privgraphy.PrivGaddEdg("b", "d");
        privgraphy.PrivGaddEdg("b", "e");
        privgraphy.PrivGaddEdg("c", "d");
        privgraphy.PrivGaddEdg("d", "e");
        privgraphy.PrivGaddEdg("g", "e");
        privgraphy.PrivGaddEdg("f", "g");
		////////////////////////////////////////
        privgraphy.prPGraphy();
		privgraphy.PrivGaddEdg(p,q);// calling for the method of Edges
		////////////////////////////////////////
		/*PrivGraphy privgraphyG = new PrivGraphy(PrivNodeGraphyGroup);
        privgraphyG.PrivGaddEdg("1", "2");
        privgraphyG.PrivGaddEdg("1", "3");
        privgraphyG.PrivGaddEdg("2", "4");
        privgraphyG.PrivGaddEdg("2", "5");
        privgraphyG.PrivGaddEdg("3", "4");
        privgraphyG.PrivGaddEdg("4", "5");
        privgraphyG.PrivGaddEdg("6", "4");
        privgraphyG.PrivGaddEdg("6", "3");
        privgraphyG.prPGraphy();*/
		System.out.println();
        System.out.println("(Non Cross-Sectional Nodes) DFS/BFS Traversal Trace:");
		privgraphy.Ptravdfs();
		System.out.println();
		}catch(Exception e){
			System.out.println("Opps! The source/destination or both have(not Exist)Untrusted connection! Try again!");
		}
    }
	
	static class PrivGraphy {
        HashMap<String, LinkedList<String>> PrivAjList = new HashMap();
        HashMap<String, Integer>  PrivGidxs = new HashMap<>();
        int idxNodes = -1;

        public PrivGraphy(ArrayList<String> PrivNodeGraphy) {
            for (int n = 0; n <PrivNodeGraphy.size() ; n++) {
                String nodePrivGraphy = PrivNodeGraphy.get(n);
                LinkedList<String> idNodeList = new LinkedList<String>();
                PrivAjList.put(nodePrivGraphy, idNodeList);
                PrivGidxs.put(nodePrivGraphy, ++idxNodes);
            }
        }

        public void PrivGaddEdg(String PrivNodeSource, String PrivNodeDEst) {
            LinkedList<String> idNodeList;
            idNodeList = PrivAjList.get(PrivNodeSource);
			idNodeList.add(0,PrivNodeDEst);
            PrivAjList.put(PrivNodeSource, idNodeList);
			
			
        }

        public void Ptravdfs(){
            int PrivNodeGraphy = PrivAjList.size();
            boolean [] PrivNodeVisit = new boolean[PrivNodeGraphy];
			
            for (Map.Entry<String, LinkedList<String>> entryPriv : PrivAjList.entrySet()) {
                String PrivNodeSource = entryPriv.getKey();
                if(!PrivNodeVisit[PrivGidxs.get(PrivNodeSource)]){
                    DFSUtil(PrivNodeSource, PrivNodeVisit);
                }
				System.out.println();
				System.out.println("----------------------------------------------------------------------------");
                System.out.print("Registered[Node] = ("+ entryPriv.getKey()+") " +"-->[Non Cross-Sectional Nodes] Cross-Sectional Nodes = " + entryPriv.getValue());
				System.out.print(" ");
            }
        }

        public void DFSUtil(String PrivNodeSource, boolean[] PrivNodeVisit){

            PrivNodeVisit[PrivGidxs.get(PrivNodeSource)] = true;
            System.out.print(PrivNodeSource + " ");
            LinkedList<String> idNodeList = PrivAjList.get(PrivNodeSource);
            for (int i = 0; i <idNodeList.size() ; i++) {
                String PrivNodeDEst = idNodeList.get(i);
                if(!PrivNodeVisit[PrivGidxs.get(PrivNodeDEst)])
                    DFSUtil(PrivNodeDEst,PrivNodeVisit);
            }
        }

        public void prPGraphy() {
            System.out.println("Registered Nodes(a,b,c,...j):");
			System.out.println("--------------------------------");
			Set<String> setPrivG = PrivAjList.keySet();
            Iterator<String> iteGraphy = setPrivG.iterator();
			
            while(iteGraphy.hasNext()){
                
				String privNode = iteGraphy.next();
				System.out.print("");
				System.out.print("[Node]: (" + privNode + ") Is Connected to Attributes ->> ");
				//System.out.println();
				//System.out.print("Registered(GROUP): (" + privNode + ") Is Connected to the Edge(Attributes)==========>>>>> ");
                LinkedList<String> Userlist = PrivAjList.get(privNode);
                for (int k = 0; k <Userlist.size() ; k++) {
                    System.out.print(Userlist.get(k) + " ");
                }
                System.out.println();
            }
        }
    }

    
}