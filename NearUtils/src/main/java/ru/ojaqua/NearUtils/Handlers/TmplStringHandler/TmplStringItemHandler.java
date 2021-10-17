package ru.ojaqua.NearUtils.Handlers.TmplStringHandler;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;

public class TmplStringItemHandler implements ITmplStringItemHandler {

	private final String outStr;
	private final ClipboardWorker clipboard;

	public TmplStringItemHandler(ClipboardWorker clipboard, String outStr) {
		super();
		this.clipboard = clipboard;
		this.outStr = outStr;
	}

	@Override
	public void run() {

		clipboard.setText(outStr);

	}
}
