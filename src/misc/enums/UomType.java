package misc.enums;


public enum UomType {
	box(1), pieces(2), stab(3), capsule(4), tablet(5), sachet(6), bag(7), jar(8);
	
	private int i;
	
	UomType(int i){
		setIndex(i);
	}
	public int getIndex(){
		return i;
	}
	public void setIndex(int index) {
		i = index;
	}
};
