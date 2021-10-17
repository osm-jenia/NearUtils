package ru.ojaqua.NearUtils.Handlers.TmplStringHandler;

import ru.ojaqua.NearUtils.Common.ClipboardWorker;

public class TmplStringItemHandlerImpl implements TmplStringItemHandler {

	private final String outStr;
	private final ClipboardWorker clipboard;

	public TmplStringItemHandlerImpl(ClipboardWorker clipboard, String outStr) {
		super();
		this.clipboard = clipboard;
		this.outStr = outStr;
	}

	@Override
	public void run() {

		clipboard.setText(outStr);

	}
}
