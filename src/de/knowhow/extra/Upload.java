package de.knowhow.extra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import de.knowhow.base.Constants;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Attachment;
import de.knowhow.model.AttachmentList;
import de.knowhow.model.db.DAO;

public class Upload implements Runnable {

	private static Splash splash;
	private DAO db;
	private boolean isImage;
	private File file;
	private AttachmentList attl;
	private int currID;

	public Upload(DAO db, boolean isImage, File file, AttachmentList al,
			int currID) {
		this.db = db;
		this.isImage = isImage;
		this.file = file;
		this.attl = al;
		this.currID = currID;
	}

	@Override
	public void run() {
		try {
			FileInputStream is = new FileInputStream(file);
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				throw new DatabaseException(
						Constants.getText("message.error.sizeError"));
			}
			splash = new Splash(Constants.createImageIcon(
					"/de/knowhow/resource/img/loading.png").getImage(), "");
			splash.setVisible(true);
			splash.showStatus("Uploading...", 101);
			byte[] bytes = new byte[(int) length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			if (offset < bytes.length) {
				throw new DatabaseException(
						Constants.getText("message.error.readError") + " "
								+ file.getName());
			}
			Attachment attach = new Attachment(this.db, file.getName(), currID,
					bytes, isImage);
			attl.addAttachment(attach);
			attl.reload();
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		splash.close();
	}
}
