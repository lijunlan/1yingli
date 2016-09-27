package cn.yiyingli.databaselistener.handle;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import com.aliyun.drc.client.message.ByteString;
import com.aliyun.drc.client.message.DataMessage.Record;
import com.aliyun.drc.client.message.DataMessage.Record.Field;

import cn.yiyingli.odps.upload.UploadUtil;
import net.sf.json.JSONObject;

public abstract class TableService {

	protected static final SimpleDateFormat DS_DATAFORMAT = new SimpleDateFormat("yyyyMMdd");

	protected Record record;

	public TableService(Record record) {
		this.record = record;
	}

	protected Record getRecord() {
		return record;
	}

	public void deal() {
		switch (record.getOpt()) {
		case UPDATE:
			dealUpdate();
			break;
		case INSERT:
			dealInsert();
			break;
		case DELETE:
			dealDelete();
			break;
		case ROLLBACK:
			dealRollback();
			break;
		default:
			dealDefault();
			break;
		}
	}

	protected String getValue(Field field) {
		String data = "";
		ByteString bs = field.getValue();
		if (bs != null) {
			try {
				data = new String(field.getValue().getBytes(), field.getEncoding());
			} catch (UnsupportedEncodingException ex) {
				data = new String(field.getValue().getBytes());
			}
		}
		return data;
	}

	protected void upLoadData(JSONObject data) {
		boolean flag = true;
		while (flag) {
			flag = !UploadUtil.uploadData(data);
			if (flag) {
				System.out.println("\tdata:" + data + "\n\tupload failed");
			}
		}
	}

	protected abstract void dealDelete();

	protected abstract void dealUpdate();

	protected abstract void dealInsert();

	protected abstract void dealRollback();

	protected abstract void dealDefault();
}
