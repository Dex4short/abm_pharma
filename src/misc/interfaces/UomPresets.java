package misc.interfaces;

import misc.objects.Uom;

public interface UomPresets {
	public final Uom Uom_Preset[] = {
			new Uom("box", 1, null),
			new Uom("pieces", 1, null),
			new Uom("box", 1, new Uom("pieces", 10, null)),
			new Uom("box", 1, new Uom("stab", 10, new Uom("capsule", 10, null))),
			new Uom("box", 1, new Uom("stab", 10, new Uom("tablet", 10, null))),
			new Uom("box", 1, new Uom("sachet", 10, null)),
			new Uom("stab", 1, new Uom("capsule", 10, null)),
			new Uom("stab", 1, new Uom("tablet", 10, null)),
			new Uom("capsule", 1, null),
			new Uom("tablet", 1, null),
			new Uom("sachet", 1, null),
			new Uom("jar", 1, null),
	};
	public final String Uom_Sizes[] = {"4", "6", "8", "10", "12", "14"};
}
