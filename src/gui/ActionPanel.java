package gui;

public abstract class ActionPanel extends TittledPanel{
	private static final long serialVersionUID = -7533357844293790225L;
	private Link cancel_lnk;
	private Button ok_btn;

	public ActionPanel(String tittle) {
		super(tittle);
		
		cancel_lnk = new Link("cancel") {
			private static final long serialVersionUID = -8748338256724533690L;
			@Override
			public void directory() {
				onCancel();
			}
		};
		
		ok_btn = new Button("ok") {
			private static final long serialVersionUID = 7991864203137196106L;
			@Override
			public void onAction() {
				onOk();
			}
		};

		add(cancel_lnk);
		add(ok_btn);
	}
	@Override
	public void onResizeTitledPanel(int w, int h) {
		getTittleLabel().setBounds(getMargine(), 0, w - (getMargine()*2), 30);
		getTittleLabel().repaint();
		getContentPane().setBounds(getMargine(), getTittleLabel().getHeight() + getMargine(), getTittleLabel().getWidth(), h-getTittleLabel().getHeight()-30-(getMargine()*2));
		getContentPane().repaint();

		cancel_lnk.setBounds(w-100-(getMargine()*2), h-getMargine()-30, 50, 30);
		ok_btn.setBounds(w-50-getMargine(), h-getMargine()-30, 50, 30);

		repaint();
	}
	public abstract void onOk();
	public abstract void onCancel();
	
}
