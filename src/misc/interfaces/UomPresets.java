package misc.interfaces;

import misc.enums.UomType;
import misc.objects.Uom;

public interface UomPresets {
	public final Uom Uom_Preset[] = {
			new Uom(-1, UomType.box, 1, null),
			new Uom(-1, UomType.pieces, 1, null),
			new Uom(-1, UomType.box, 1, new Uom(-1, UomType.pieces, -1, null)),
			new Uom(-1, UomType.box, 1, new Uom(-1, UomType.stab, -1, new Uom(-1, UomType.capsule, 10, null))),
			new Uom(-1, UomType.box, 1, new Uom(-1, UomType.stab, -1, new Uom(-1, UomType.tablet, 10, null))),
			new Uom(-1, UomType.box, 1, new Uom(-1, UomType.sachet, -1, null)),
			new Uom(-1, UomType.stab, 1, new Uom(-1, UomType.capsule, 10, null)),
			new Uom(-1, UomType.stab, 1, new Uom(-1, UomType.tablet, 10, null)),
			new Uom(-1, UomType.capsule, 1, null),
			new Uom(-1, UomType.tablet, 1, null),
			new Uom(-1, UomType.bag, 1, null),
			new Uom(-1, UomType.bag, 1, new Uom(-1, UomType.sachet, -1, null)),
			new Uom(-1, UomType.sachet, 1, null),
			new Uom(-1, UomType.jar, 1, null),
	};
	public final String Uom_Sizes[] = {"4", "6", "8", "10", "12", "14"};	
	
	public default void prepareIds(Uom uom) {
		if(uom != null) {
			int
			index = uom.getUnitType().getIndex(),
			size = uom.getUnitSize();
			
			String id = index + "" + size;
			String new_id;
			
			Uom sub_uom = uom.getSubUom();
			
			prepareIds(sub_uom);
			
			if(sub_uom != null) {
				new_id = id + sub_uom.getUomId();
			}
			else {
				new_id = id;
			}
			
			uom.setUomId(Integer.parseInt(new_id));
		}
	}
}
