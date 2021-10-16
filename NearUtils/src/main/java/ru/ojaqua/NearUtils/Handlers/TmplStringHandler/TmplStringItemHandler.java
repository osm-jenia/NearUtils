package ru.ojaqua.NearUtils.Handlers.TmplStringHandler;

import ru.ojaqua.NearUtils.Common.IClipboardWorker;

public class TmplStringItemHandler implements ITmplStringItemHandler {

	private final String outStr;
	private final IClipboardWorker clipboard;

	public TmplStringItemHandler(IClipboardWorker clipboard, String outStr) {
		super();
		this.clipboard = clipboard;
		this.outStr = outStr;
	}

	@Override
	public void run() {

		clipboard.setText(outStr);

	}
}
