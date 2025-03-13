package Nodes_Edges;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectedNode extends AbstractNode {

    //--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    //private Map<DirectedNode, Integer> succs;
    //private Map<DirectedNode, Integer> preds;
	//private List<DirectedNode> succs;
	//private List<DirectedNode> preds;
	private List<Arc> arcSucc; // List of out-going arc from the node this.
	private List<Arc> arcPred; // List of in-going arc from the node this.

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public DirectedNode(int i) {
        super(i);
      //  this.succs = new LinkedList<DirectedNode>();
      //  this.preds = new LinkedList<DirectedNode>();
        this.arcSucc = new LinkedList<Arc>();
        this.arcPred = new LinkedList<Arc>();
    }
    
    
    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    /**
     * @return the list of successors of the current node this
     */
   /* public List<DirectedNode> getSuccs() {
        return this.succs;
    }*/

    /**
     * @return the list of predecessors of the current node this
     */
    /*public List<DirectedNode> getPreds() {
        return preds;
    }*/

    /**
     * @return the number of successors of node this
     */
    public int getNbSuccs() {
        return arcSucc.size();
    }
    
    /**
     * @return the number of predecessors of node this
     */
    public int getNbPreds() {
        return arcPred.size();
    }

   
	/** 
	 * @return the list of out-going arcs that are successors of the current node represented by "this"
	 */
	public List<Arc> getArcSucc() {
		return this.arcSucc;
	}
	
	/** 
	 * @return the list of in-going arcs that are predecessors of the current node represented by "this"
	 */
	public List<Arc> getArcPred() {
		return this.arcPred;
	}
    		
	
    /**
     * @param suc the new list of successors for node this
     */
    /*public void setSuccs(List<DirectedNode> suc) {
        this.succs = suc;
    }*/

    /**
     * @param pre the new list of predecessors for nodes this
     */
    /*public void setPreds(List<DirectedNode> pre) {
        this.preds = pre;
    }*/


    // ------------------------------------------
    // 				Methods
    // ------------------------------------------
    
    
      
    /**
	 * add a new successor if it does not exist already.
	 */
	/*public void addSucc(DirectedNode v) {
		if(!this.succs.contains(v)) {
			this.succs.add(v);
		}
	}*/
   
	/**
	 * add a new predecessor if it does not exist already.
	 */
	/*public void addPred(DirectedNode v) {
		if(!this.preds.contains(v)) {
			this.preds.add(v);
		}
	}*/
	
	/**
	 * add a new edge with its value cost. If the successor exists, the weight is changed.
	 */
	public void addArc(Arc a1) {
		if(a1.getFirstNode().equals(this)) { // case where the arc is out-going from "this".
			if(!this.getArcSucc().contains(a1)) {		
				/*if(!this.succs.contains(a1.getSecondNode())) { // If the successor was not in the list, we add it.	
					this.addSucc(a1.getSecondNode());				
				}*/				
				this.arcSucc.add(a1);
			}
			else {
				// Update of the existing weight
				int indexEdge = this.getArcSucc().indexOf(a1);
				this.getArcSucc().get(indexEdge).setWeight(a1.getWeight());
			}
		}
		else if(a1.getSecondNode().equals(this)) { // case where the arc is in-going to "this".
			if(!this.getArcPred().contains(a1)) {		
				/*if(!this.preds.contains(a1.getFirstNode())) { // If the predecessor was not in the list, we add it.				
					this.addPred(a1.getFirstNode());				
				}*/				
				this.arcPred.add(a1);
			}
			else {
				// Update of the existing weight
				int indexEdge = this.getArcPred().indexOf(a1);
				this.getArcPred().get(indexEdge).setWeight(a1.getWeight());
			}
		}
				
	}
	

}
