package de.knowhow.extra;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import de.knowhow.base.Constants;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.Attachment;
import de.knowhow.model.AttachmentList;
import de.knowhow.model.db.DAO;
import de.knowhow.view.Splash;

public class Upload implements Runnable {

	private static Splash splash;
	private DAO db;
	private boolean isImage;
	private File file;
	private AttachmentList al;
	private int currID;

	public Upload(DAO db, boolean isImage, File file, AttachmentList al,
			int currID) {
		this.db = db;
		this.isImage = isImage;
		this.file = file;
		this.al = al;
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
			URL url = ClassLoader
					.getSystemResource("de/knowhow/resource/img/loading.png");
			Image image = null;
			if (url != null)
				try {
					image = ImageIO.read(url);
				} catch (IOException ex) {
				}
			splash = new Splash(image, "");
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
			al.addAttachment(attach);
			al.reload();
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
