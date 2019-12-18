//====================================================================================================================================================
// CS5413 : pgm1 - problem I written by Ali Alsahlanee, Fadhil (PART 1)
// Semester : Fall 2019
// Prof. N.Park
// Three programs: Assignment Identity Management, Privacy and Anonymity Graphs
// Description:
// This code will take the inputs Userslist: a,b,c,d,e,f,g,h,i,j represents users and/or Groupslist:1,2,3,4,5,6,7,8,9,10
// First of all, enter the source and destination to make a trusted list of the Registed Nodes (users/groups)
// which shows the connected edges with trusted vs. untrusted nodes and compared them all.
//=====================================================================================================================================================
import java.util.*;
import java.io.*;
@SuppressWarnings("unchecked")
public class PrivacyGraphIDs1 {

    public static void main(String[] args)throws Exception {
       ArrayList<String> PrivNodeGraphyUser = new ArrayList<String>(100);// this Arraylist for holding the users nodes
	   ArrayList<String> PrivNodeGraphyGroup = new ArrayList<String>(100);// this Arraylist fro holding the groups nodes
			
		PrivNodeGraphyUser.add("a");// the first node (initialized) as root for the users
        PrivNodeGraphyUser.add("b");
        PrivNodeGraphyUser.add("c");
        PrivNodeGraphyUser.add("d");
        PrivNodeGraphyUser.add("e");
        PrivNodeGraphyUser.add("f");
        PrivNodeGraphyUser.add("g");
		PrivNodeGraphyUser.add("h");
		PrivNodeGraphyUser.add("i");
		PrivNodeGraphyUser.add("j");
		
		PrivNodeGraphyGroup.add("1");// the first node (initialized) as root for the groups
        PrivNodeGraphyGroup.add("2");
        PrivNodeGraphyGroup.add("3");
        PrivNodeGraphyGroup.add("4");
        PrivNodeGraphyGroup.add("5");
        PrivNodeGraphyGroup.add("6");
        PrivNodeGraphyGroup.add("7");
		PrivNodeGraphyGroup.add("8");
		PrivNodeGraphyGroup.add("9");
		PrivNodeGraphyGroup.add("10");
		
		try{
			Scanner scinputes = new Scanner(System.in);
			System.out.print("Enter a source: ");
			String p = scinputes.nextLine();
			System.out.print("Enter a destination: ");
			String q = scinputes.nextLine();
		
			
			
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
		
        privgraphy.prPGraphy();
		privgraphy.PrivGaddEdg(p,q);// calling for the method of the edges
		
		
		
		PrivGraphy privgraphyG = new PrivGraphy(PrivNodeGraphyGroup);
        privgraphyG.PrivGaddEdg("1", "2");
        privgraphyG.PrivGaddEdg("1", "3");
        privgraphyG.PrivGaddEdg("2", "4");
        privgraphyG.PrivGaddEdg("2", "5");
        privgraphyG.PrivGaddEdg("3", "4");
        privgraphyG.PrivGaddEdg("4", "5");
        privgraphyG.PrivGaddEdg("6", "4");
        privgraphyG.PrivGaddEdg("6", "3");
        privgraphyG.prPGraphy();
		System.out.println();
        System.out.println("::::::::::::::::::::::::::::(Trusted) DFS/BFS Traversal Trace::::::::::::::::::::::::::::::::::::::::::::");
		privgraphy.Ptravdfs();
		System.out.println();
		}catch(Exception e){
			System.out.println("Opps! The source/destination or both have untrusted connection! Try again!");
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
                System.out.print("Registered(USER/GROUP)Node = ("+ entryPriv.getKey()+") " +", Has Permission Attribute/USER/GROUP = " + entryPriv.getValue());
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
            Set<String> setPrivG = PrivAjList.keySet();
            Iterator<String> iteGraphy = setPrivG.iterator();

            while(iteGraphy.hasNext()){
                String privNode = iteGraphy.next();
				System.out.println();
                System.out.print("Registered(USER): (" + privNode + ") Is Connected to the Edge(Attributes)==========>>>>> ");
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