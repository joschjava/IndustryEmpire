package objects;

import java.util.ArrayList;
import java.util.Optional;

import game.Resource;
import game.ResourceSpec;

//Maybe change to HashMap?
public class ResourceList extends ArrayList<Resource> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int PLUS = 1;
	public static final int MINUS = -1;
	
	
	
	@Override
	@Deprecated
	public boolean add(Resource e) {
		System.err.println("Depricated method: ResourceList.add()"
				+ "Use chgResourceAmountBy instead");
		return false;
	}

	@Override
	@Deprecated
	public Resource get(int index) {
		System.err.println("Depricated method: ResourceList.get()"
				+ "Use getElementByResSpec instead");
		return null;
	}

	public Resource chgResourceAmountBy(Resource res, int direction) {
		Optional<Resource> element = findResource(res);
		/** Resource that were possible to change */
		Resource chgResources = null;
		if(element.isPresent()) {
			chgResources = element.get();
			double availableRes = chgResources.getAmount();
			if(direction == MINUS && availableRes-res.getAmount() <= 0) { // if there are less resources than needed
				chgResources.chgResource(-availableRes); // take all that are needed
				chgResources = new Resource(res.getSpec(), availableRes);
			} else {
				chgResources.chgResource(direction*res.getAmount());
				chgResources = res;
			}
			
			//TODO: Performance issue: Should you remove?
//			if(Resource.isZero(loadRes)) {
//				super.remove(loadRes);
//			}
		} else {
			if(direction == PLUS) {
				chgResources = res.getCopy();
				super.add(chgResources);
			} else {
				chgResources = new Resource(res.getSpec(), 0.0);
			}
		}
		//TODO: Possible Performance issue
		return chgResources.getCopy();
	}
	
	public ArrayList<Resource> getAllResources(){
		return this;
	}
	
	
	/**
	 * Returns a resource by its specification
	 * @param resSpec
	 * @return
	 */
	public Resource getElementByResSpec(ResourceSpec resSpec) {
		Optional<Resource> element = findResource(resSpec);
		if(element.isPresent()) {
			return element.get();
		} else {
			// If resource doesn't exist, create, so it can be managed
			System.err.println("Resource '"+resSpec.getName()+"' didn't exist and needed to be created, was this intended?");
			Resource res = new Resource(resSpec, 0.0);
			chgResourceAmountBy(res, PLUS);
			return res;
		}
	}

	/**
	 * Returns how much of the given Resource exists
	 * @param resSpec
	 * @return
	 */
	public double getResAmountByResSpec(ResourceSpec resSpec) {
		Optional<Resource> element = findResource(resSpec);
		if(element.isPresent()) {
			return element.get().getAmount();
		} else {
			return 0.0;
		}
	}
	
	private Optional<Resource> findResource(Resource res) {
		return findResource(res.getSpec());
	}
	
	private Optional<Resource> findResource(ResourceSpec resSpec) {
		return super.stream()
		.filter(findRes -> resSpec == findRes.getSpec())
		.reduce((a, b) -> {
			throw new IllegalStateException("Multiple Resource of same ResourceSpec: " + a + ", " + b);
		});
	}
	
	/**
	 * Remove all resources from list
	 */
	public void clearList() {
		super.clear();
	}
	
	/**
	@deprecated use clearLIst
	*/
	@Override
	public void clear() {
		System.err.println("Use clearList() instead of clear");
	}
	
	/**
	 * Sets the amount of a specific resource
	 * @param res
	 * @deprecated Use for debugging only
	 */
	public void setResourceAmount(Resource res) {
		Optional<Resource> element = findResource(res);
		if(element.isPresent()) {
			element.get().setAmount(res.getAmount());
		} else {
			super.add(res.getCopy());
		}
	}
	
	
}
