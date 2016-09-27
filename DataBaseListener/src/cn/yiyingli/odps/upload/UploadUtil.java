package cn.yiyingli.odps.upload;

import java.io.IOException;
import java.util.Date;

import com.aliyun.odps.Column;
import com.aliyun.odps.Instance;
import com.aliyun.odps.Odps;
import com.aliyun.odps.OdpsException;
import com.aliyun.odps.PartitionSpec;
import com.aliyun.odps.TableSchema;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.data.Record;
import com.aliyun.odps.data.RecordWriter;
import com.aliyun.odps.task.SQLTask;
import com.aliyun.odps.tunnel.TableTunnel;
import com.aliyun.odps.tunnel.TableTunnel.UploadSession;
import com.aliyun.odps.tunnel.TunnelException;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class UploadUtil {

	public static String ACCESSID = "T8Idmm00U1mAwzcn";

	public static String ACCESSKEY = "cZQkX1saEq1eF2g1ADbnO2kcFlJxb9";

	private static String tunnelUrl = "http://dt.odps.aliyun.com";

	private static String odpsUrl = "http://odps-ext.aliyun-inc.com/api";
	//
	// private static String odpsUrl = "http://service.odps.aliyun.com/api";

	private static String project = "yiyinglirecommend";

	private static void odpsPrepare(Odps odps, String table, String partition)
			throws OdpsException {
		Instance instance = SQLTask.run(odps, "alter table " + table
				+ " add if not exists partition (" + partition + ");");
		instance.waitForSuccess();
	}

	public static boolean uploadData(JSONObject data) {
		Account account = new AliyunAccount(ACCESSID, ACCESSKEY);
		Odps odps = new Odps(account);
		odps.setEndpoint(odpsUrl);
		odps.setDefaultProject(project);
		try {
			odpsPrepare(odps, data.getString("table"),
					data.getString("partition"));
			TableTunnel tunnel = new TableTunnel(odps);
			tunnel.setEndpoint(tunnelUrl);
			PartitionSpec partitionSpec = new PartitionSpec(
					data.getString("partition"));
			UploadSession uploadSession = tunnel.createUploadSession(project,
					data.getString("table"), partitionSpec);

			TableSchema schema = uploadSession.getSchema();
			RecordWriter recordWriter = uploadSession.openRecordWriter(0);
			Record record = uploadSession.newRecord();
			JSONArray clist = data.getJSONArray("data");
			for (int i = 0; i < schema.getColumns().size(); i++) {
				Column column = schema.getColumn(i);
				switch (column.getType()) {
				case BIGINT:
					try {
						long d = clist.getLong(i);
						record.setBigint(i, d);
					} catch (JSONException e) {
						record.setBigint(i, 0L);
					}
					record.setBigint(i, clist.getLong(i));
					break;
				case BOOLEAN:
					try {
						boolean d = clist.getBoolean(i);
						record.setBoolean(i, d);
					} catch (JSONException e) {
						record.setBoolean(i, false);
					}
					break;
				case DOUBLE:
					try {
						double d = clist.getDouble(i);
						record.setDouble(i, d);
					} catch (JSONException e) {
						record.setDouble(i, 0D);
					}
					break;
				case STRING:
					record.setString(
							i,
							clist.getString(i).equals("null") ? null : clist
									.getString(i));
					break;
				case DATETIME:
					try {
						record.setDatetime(i,
								new Date(Long.valueOf(clist.getString(i))));
					} catch (Exception e) {
						record.setDatetime(i, new Date());
					}
					break;
				default:
					throw new RuntimeException("Unknown column type: "
							+ column.getType());
				}
			}
			recordWriter.write(record);
			recordWriter.close();
			uploadSession.commit(new Long[] { 0L });
		} catch (TunnelException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (OdpsException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
