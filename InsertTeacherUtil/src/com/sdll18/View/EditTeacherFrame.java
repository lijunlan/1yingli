package com.sdll18.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sdll18.Data.ManagerData;
import com.sdll18.Util.Json;
import com.sdll18.Util.MsgUtil;
import com.sdll18.Util.UploadUtil;
import com.sdll18.View.SEDialog.SEDialogListener;
import com.sdll18.View.WEDialog.WEDialogListener;

public class EditTeacherFrame implements SEDialogListener, WEDialogListener {

	private JFrame mFrame;

	private JSONArray weList = new JSONArray();

	private JSONArray seList = new JSONArray();

	private JTextArea textAreaWorkExp;

	private JTextArea textAreaStudyExp;

	private JTextField textFieldUsername;

	private JTextArea textAreaIntroduce;

	private JTextField textFieldShowWeight1;
	private JTextField textFieldShowWeight2;
	private JTextField textFieldShowWeight4;
	private JTextField textFieldShowWeight8;
	private JTextField textFieldShowWeight16;
	private JTextField textFieldSimpleInfo;
	private JTextField textFieldName;
	private JTextField textFieldPhone;
	private JTextField textFieldAddress;
	private JTextField textFieldEmail;
	private JTextField textFieldIcon;
	private JTextField textFieldTitle;
	private JTextField textFieldTime;
	private JTextField textFieldPrice;
	private JTextField textFieldTimePerWeek;
	private JTextArea textFieldContent;

	public EditTeacherFrame() {
		mFrame = new JFrame("EditTeacher");
		// mFrame.setResizable(false);
		mFrame.setSize(1300, 700);
		mFrame.setTitle("EditTeacher");
		mFrame.setLayout(null);
		mFrame.setLocationRelativeTo(null);

		JLabel labelName = new JLabel("姓名");
		JLabel labelSimpleinfo = new JLabel("显示的经历");
		JLabel labelPhone = new JLabel("手机");
		JLabel labelAddress = new JLabel("常住地址");
		JLabel labelEmail = new JLabel("邮箱");
		JLabel labelServiceTitle = new JLabel("话题(title)");
		JLabel labelServiceTime = new JLabel("时间(once)");
		JLabel labelServicePrice = new JLabel("价格(once)");
		JLabel labelServiceTimePerWeek = new JLabel("次数(week)");
		JLabel labelServiceContent = new JLabel("服务内容");
		JLabel labelIcon = new JLabel("照片URL");
		JLabel labelShowWeight1 = new JLabel("显示权重(留)");
		JLabel labelShowWeight2 = new JLabel("显示权重(求)");
		JLabel labelShowWeight4 = new JLabel("显示权重(创)");
		JLabel labelShowWeight8 = new JLabel("显示权重(校)");
		JLabel labelShowWeight16 = new JLabel("显示权重(猎)");

		labelShowWeight1.setBounds(910, 500, 80, 20);
		labelShowWeight2.setBounds(910, 560, 80, 20);
		labelShowWeight4.setBounds(910, 620, 80, 20);
		labelShowWeight8.setBounds(1010, 500, 80, 20);
		labelShowWeight16.setBounds(1010, 560, 80, 20);

		labelSimpleinfo.setBounds(30, 0, 100, 20);
		labelName.setBounds(30, 30, 100, 20);
		labelPhone.setBounds(30, 60, 100, 20);
		labelAddress.setBounds(30, 90, 100, 20);
		labelEmail.setBounds(30, 120, 100, 20);
		labelIcon.setBounds(30, 150, 60, 20);
		labelServiceTitle.setBounds(30, 180, 100, 20);
		labelServiceTime.setBounds(30, 210, 100, 20);
		labelServicePrice.setBounds(30, 240, 100, 20);
		labelServiceTimePerWeek.setBounds(30, 270, 100, 20);
		labelServiceContent.setBounds(30, 300, 100, 20);

		mFrame.add(labelShowWeight1);
		mFrame.add(labelShowWeight2);
		mFrame.add(labelShowWeight4);
		mFrame.add(labelShowWeight8);
		mFrame.add(labelShowWeight16);

		mFrame.add(labelSimpleinfo);
		mFrame.add(labelName);
		mFrame.add(labelPhone);
		mFrame.add(labelAddress);
		mFrame.add(labelEmail);
		mFrame.add(labelServiceTitle);
		mFrame.add(labelServiceTime);
		mFrame.add(labelServicePrice);
		mFrame.add(labelServiceTimePerWeek);
		mFrame.add(labelServiceContent);
		mFrame.add(labelIcon);

		textFieldShowWeight1 = new JTextField();
		textFieldShowWeight2 = new JTextField();
		textFieldShowWeight4 = new JTextField();
		textFieldShowWeight8 = new JTextField();
		textFieldShowWeight16 = new JTextField();

		textFieldSimpleInfo = new JTextField();
		textFieldName = new JTextField();
		textFieldPhone = new JTextField();
		textFieldAddress = new JTextField();
		textFieldEmail = new JTextField();
		textFieldIcon = new JTextField();
		textFieldTitle = new JTextField();
		textFieldTime = new JTextField();
		textFieldPrice = new JTextField();
		textFieldTimePerWeek = new JTextField();
		textFieldContent = new JTextArea();
		textFieldContent.setWrapStyleWord(true);
		textFieldContent.setLineWrap(true);

		JButton btnUploadImage = new JButton("上传图片");
		btnUploadImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.showDialog(new JLabel(), "Choose an image");
				File file = jfc.getSelectedFile();
				if (file == null)
					return;
				if (file.getName().endsWith(".png")
						|| file.getName().endsWith(".jpg")
						|| file.getName().endsWith(".bmp")) {
					try {
						String url = UploadUtil.postFile(file.getPath(), "1");
						textFieldIcon.setText(url);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(mFrame, "Choose an image！");
				}
			}
		});

		textFieldShowWeight1.setBounds(910, 530, 100, 20);
		textFieldShowWeight2.setBounds(910, 590, 100, 20);
		textFieldShowWeight4.setBounds(910, 650, 100, 20);
		textFieldShowWeight8.setBounds(1010, 530, 100, 20);
		textFieldShowWeight16.setBounds(1010, 590, 100, 20);

		textFieldSimpleInfo.setBounds(150, 0, 180, 20);
		textFieldName.setBounds(150, 30, 180, 20);
		textFieldPhone.setBounds(150, 60, 180, 20);
		textFieldAddress.setBounds(150, 90, 180, 20);
		textFieldEmail.setBounds(150, 120, 180, 20);
		textFieldIcon.setBounds(150, 150, 180, 20);
		textFieldTitle.setBounds(150, 180, 180, 20);
		textFieldTime.setBounds(150, 210, 180, 20);
		textFieldPrice.setBounds(150, 240, 180, 20);
		textFieldTimePerWeek.setBounds(150, 270, 180, 20);
		JScrollPane sp = new JScrollPane(textFieldContent);

		sp.setBounds(150, 300, 250, 100);

		btnUploadImage.setBounds(340, 150, 60, 20);

		mFrame.add(textFieldShowWeight1);
		mFrame.add(textFieldShowWeight2);
		mFrame.add(textFieldShowWeight4);
		mFrame.add(textFieldShowWeight8);
		mFrame.add(textFieldShowWeight16);
		mFrame.add(textFieldSimpleInfo);
		mFrame.add(textFieldName);
		mFrame.add(textFieldPhone);
		mFrame.add(textFieldAddress);
		mFrame.add(textFieldEmail);
		mFrame.add(textFieldIcon);
		mFrame.add(textFieldTitle);
		mFrame.add(textFieldTime);
		mFrame.add(textFieldPrice);
		mFrame.add(textFieldTimePerWeek);
		mFrame.add(sp);

		mFrame.add(btnUploadImage);

		textAreaWorkExp = new JTextArea(30, 10);
		textAreaStudyExp = new JTextArea(30, 10);
		textAreaWorkExp.setLineWrap(true);
		textAreaStudyExp.setLineWrap(true);
		textAreaWorkExp.setWrapStyleWord(true);
		textAreaStudyExp.setWrapStyleWord(true);
		textAreaWorkExp.setEditable(false);
		textAreaStudyExp.setEditable(false);

		JScrollPane scrollPaneWE = new JScrollPane(textAreaWorkExp);
		JScrollPane scrollPaneSE = new JScrollPane(textAreaStudyExp);

		scrollPaneWE.setBounds(400, 30, 500, 250);
		scrollPaneSE.setBounds(400, 350, 500, 250);
		// textAreaStudyExp.setBounds(400, 30,500,250);
		// textAreaWorkExp.setBounds(400, 350,500,250);

		mFrame.add(scrollPaneSE);
		mFrame.add(scrollPaneWE);

		final JCheckBox checkBoxTip1 = new JCheckBox("留学领航");
		final JCheckBox checkBoxTip2 = new JCheckBox("求职就业");
		final JCheckBox checkBoxTip4 = new JCheckBox("创业助力");
		final JCheckBox checkBoxTip8 = new JCheckBox("校园生活");
		final JCheckBox checkBoxTip16 = new JCheckBox("猎奇分享");
		final JCheckBox checkBoxTip32 = new JCheckBox("学霸");
		final JCheckBox checkBoxTip64 = new JCheckBox("奇葩");
		final JCheckBox checkBoxTip128 = new JCheckBox("开飞机");
		final JCheckBox checkBoxTip256 = new JCheckBox("跑酷");
		final JCheckBox checkBoxTip512 = new JCheckBox("赛车");
		final JCheckBox checkBoxTip1024 = new JCheckBox("潜水");
		final JCheckBox checkBoxTip2048 = new JCheckBox("骑马");
		checkBoxTip1.setBounds(30, 400, 100, 30);
		checkBoxTip2.setBounds(150, 400, 100, 30);
		checkBoxTip4.setBounds(270, 400, 100, 30);
		checkBoxTip8.setBounds(30, 430, 100, 30);
		checkBoxTip16.setBounds(150, 430, 100, 30);
		checkBoxTip32.setBounds(270, 430, 100, 30);
		checkBoxTip64.setBounds(30, 470, 100, 30);
		checkBoxTip128.setBounds(150, 470, 100, 30);
		checkBoxTip256.setBounds(270, 470, 100, 30);
		checkBoxTip512.setBounds(30, 530, 100, 30);
		checkBoxTip1024.setBounds(150, 530, 100, 30);
		checkBoxTip2048.setBounds(270, 530, 100, 30);

		mFrame.add(checkBoxTip1);
		mFrame.add(checkBoxTip2);
		mFrame.add(checkBoxTip4);
		mFrame.add(checkBoxTip8);
		mFrame.add(checkBoxTip16);
		mFrame.add(checkBoxTip32);
		mFrame.add(checkBoxTip64);
		mFrame.add(checkBoxTip128);
		mFrame.add(checkBoxTip256);
		mFrame.add(checkBoxTip512);
		mFrame.add(checkBoxTip1024);
		mFrame.add(checkBoxTip2048);

		JLabel labelUsername = new JLabel("用户名");
		labelUsername.setBounds(30, 570, 100, 20);

		mFrame.add(labelUsername);

		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(80, 570, 180, 20);

		mFrame.add(textFieldUsername);

		JLabel labelIntroduce = new JLabel("自我介绍");
		labelIntroduce.setBounds(910, 200, 100, 20);

		mFrame.add(labelIntroduce);

		final JCheckBox checkBoxCheckPhone = new JCheckBox("认证电话");
		final JCheckBox checkBoxCheckEmail = new JCheckBox("认证邮箱");
		final JCheckBox checkBoxCheckIDCard = new JCheckBox("认证身份证");
		final JCheckBox checkBoxCheckWork = new JCheckBox("认证职业");
		final JCheckBox checkBoxCheckStudy = new JCheckBox("认证教育");

		checkBoxCheckPhone.setBounds(910, 30, 110, 30);
		checkBoxCheckEmail.setBounds(1030, 30, 110, 30);
		checkBoxCheckIDCard.setBounds(1150, 30, 110, 30);
		checkBoxCheckWork.setBounds(910, 70, 110, 30);
		checkBoxCheckStudy.setBounds(1030, 70, 110, 30);

		mFrame.add(checkBoxCheckPhone);
		mFrame.add(checkBoxCheckEmail);
		mFrame.add(checkBoxCheckIDCard);
		mFrame.add(checkBoxCheckWork);
		mFrame.add(checkBoxCheckStudy);

		JButton btnGetTeacherInfo = new JButton("获取导师信息");
		btnGetTeacherInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject toSend = new JSONObject();
				toSend.put("style", "manager");
				toSend.put("method", "getTeacherInfo");
				toSend.put("username", textFieldUsername.getText());
				toSend.put("mid", ManagerData.MID);
				String json = MsgUtil.sendMsg(toSend.toString());
				checkBoxTip1.setSelected(false);
				checkBoxTip2.setSelected(false);
				checkBoxTip4.setSelected(false);
				checkBoxTip8.setSelected(false);
				checkBoxTip16.setSelected(false);
				checkBoxTip32.setSelected(false);
				checkBoxTip64.setSelected(false);
				checkBoxTip128.setSelected(false);
				checkBoxTip256.setSelected(false);
				checkBoxTip512.setSelected(false);
				checkBoxTip1024.setSelected(false);
				checkBoxTip2048.setSelected(false);
				JSONObject data = JSONObject.fromObject(json);
				if (data.get("state").equals("error")) {
					JOptionPane.showMessageDialog(mFrame, data.get("msg"));
					return;
				}
				JSONObject t = (JSONObject) data.get("teacher");
				String name = t.getString("name");
				String phone = (String) t.get("phone");
				String address = (String) t.get("address");
				String email = (String) t.get("email");
				String iconUrl = (String) t.get("iconUrl");
				String introduce = (String) t.get("introduce");
				String checkIDCard = (String) t.get("checkIDCard");
				String checkStudy = (String) t.get("checkStudy");
				String checkWork = (String) t.get("checkWork");
				String checkPhone = (String) t.get("checkPhone");
				String checkEmail = (String) t.get("checkEmail");
				String serviceTitle = (String) t.get("serviceTitle");
				String serviceTime = (String) t.get("serviceTime");
				String servicePrice = (String) t.get("servicePrice");
				String serviceTimePerWeek = (String) t
						.get("serviceTimePerWeek");
				String serviceContent = (String) t.get("serviceContent");
				String showWeight1 = String.valueOf((Integer) t
						.get("showWeight1"));
				String showWeight2 = String.valueOf((Integer) t
						.get("showWeight2"));
				String showWeight4 = String.valueOf((Integer) t
						.get("showWeight4"));
				String showWeight8 = String.valueOf((Integer) t
						.get("showWeight8"));
				String showWeight16 = String.valueOf((Integer) t
						.get("showWeight16"));
				String simpleinfo = (String) t.get("simpleinfo");

				textFieldName.setText(name);
				textFieldPhone.setText(phone);
				textFieldAddress.setText(address);
				textFieldEmail.setText(email);
				textFieldIcon.setText(iconUrl);
				textFieldShowWeight1.setText(showWeight1);
				textFieldShowWeight2.setText(showWeight2);
				textFieldShowWeight4.setText(showWeight4);
				textFieldShowWeight8.setText(showWeight8);
				textFieldShowWeight16.setText(showWeight16);
				textFieldSimpleInfo.setText(simpleinfo);
				textAreaIntroduce.setText(introduce);

				checkBoxCheckPhone.setSelected(Boolean.valueOf(checkPhone));
				checkBoxCheckEmail.setSelected(Boolean.valueOf(checkEmail));
				checkBoxCheckIDCard.setSelected(Boolean.valueOf(checkIDCard));
				checkBoxCheckStudy.setSelected(Boolean.valueOf(checkStudy));
				checkBoxCheckWork.setSelected(Boolean.valueOf(checkWork));

				textFieldTitle.setText(serviceTitle);
				textFieldTime.setText(serviceTime);
				textFieldPrice.setText(servicePrice);
				textFieldTimePerWeek.setText(serviceTimePerWeek);
				textFieldContent.setText(serviceContent);

				JSONArray ts = (JSONArray) t.get("tips");
				for (int i = 0; i < ts.size(); i++) {
					JSONObject j = (JSONObject) ts.get(i);
					String id = j.getString("id");
					switch (id) {
					case "1":
						checkBoxTip1.setSelected(true);
						break;
					case "2":
						checkBoxTip2.setSelected(true);
						break;
					case "4":
						checkBoxTip4.setSelected(true);
						break;
					case "8":
						checkBoxTip8.setSelected(true);
						break;
					case "16":
						checkBoxTip16.setSelected(true);
						break;
					case "32":
						checkBoxTip32.setSelected(true);
						break;
					case "64":
						checkBoxTip64.setSelected(true);
						break;
					case "128":
						checkBoxTip128.setSelected(true);
						break;
					case "256":
						checkBoxTip256.setSelected(true);
						break;
					case "512":
						checkBoxTip512.setSelected(true);
						break;
					case "1024":
						checkBoxTip1024.setSelected(true);
						break;
					case "2048":
						checkBoxTip2048.setSelected(true);
						break;
					}
				}

				JSONArray we = (JSONArray) t.get("workExperience");
				weList = we;

				JSONArray se = (JSONArray) t.get("studyExperience");
				seList = se;

				textAreaStudyExp.setText(seList.toString());
				textAreaWorkExp.setText(weList.toString());
			}
		});

		btnGetTeacherInfo.setBounds(300, 570, 100, 20);

		mFrame.add(btnGetTeacherInfo);

		textAreaIntroduce = new JTextArea();
		textAreaIntroduce.setLineWrap(true);
		textAreaIntroduce.setWrapStyleWord(true);
		JScrollPane jsp = new JScrollPane(textAreaIntroduce);
		jsp.setBounds(910, 230, 300, 100);

		mFrame.add(jsp);

		JButton btnAddWorkExp = new JButton("添加工作经历");
		JButton btnAddStudyExp = new JButton("添加教育经历");
		JButton btnDelWorkExp = new JButton("删除最后一条工作经历");
		JButton btnDelStudyExp = new JButton("删除最后一条教育经历");
		btnAddWorkExp.setBounds(900 - 200, 280 + 30, 200, 30);
		btnAddStudyExp.setBounds(900 - 200, 600 + 30, 200, 30);
		btnDelWorkExp.setBounds(900 - 200 - 220, 280 + 30, 200, 30);
		btnDelStudyExp.setBounds(900 - 200 - 200, 600 + 30, 200, 30);

		btnAddWorkExp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WEDialog dialog = new WEDialog(mFrame, EditTeacherFrame.this);
				dialog.start();
			}
		});

		btnAddStudyExp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SEDialog dialog = new SEDialog(mFrame, EditTeacherFrame.this);
				dialog.start();
			}
		});

		btnDelWorkExp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				weList.remove(weList.size() - 1);
				textAreaWorkExp.setText(weList.toString());
			}
		});

		btnDelStudyExp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				seList.remove(seList.size() - 1);
				textAreaStudyExp.setText(seList.toString());
			}
		});

		mFrame.add(btnAddWorkExp);
		mFrame.add(btnAddStudyExp);
		mFrame.add(btnDelWorkExp);
		mFrame.add(btnDelStudyExp);

		JButton btnSave = new JButton("提交");

		btnSave.setBounds(940, 600, 100, 30);

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject object = new JSONObject();
				object.put("name", textFieldName.getText());
				object.put("phone", textFieldPhone.getText());
				object.put("address", textFieldAddress.getText());
				object.put("email", textFieldEmail.getText());
				object.put("introduce", textAreaIntroduce.getText());
				object.put("iconUrl", textFieldIcon.getText());
				object.put("simpleinfo", textFieldSimpleInfo.getText());
				object.put("showWeight1", textFieldShowWeight1.getText());
				object.put("showWeight2", textFieldShowWeight2.getText());
				object.put("showWeight4", textFieldShowWeight4.getText());
				object.put("showWeight8", textFieldShowWeight8.getText());
				object.put("showWeight16", textFieldShowWeight16.getText());

				object.put("checkEmail",
						checkBoxCheckEmail.isSelected() ? "true" : "false");
				object.put("checkPhone",
						checkBoxCheckPhone.isSelected() ? "true" : "false");
				object.put("checkWork", checkBoxCheckWork.isSelected() ? "true"
						: "false");
				object.put("checkStudy",
						checkBoxCheckStudy.isSelected() ? "true" : "false");
				object.put("checkIDCard",
						checkBoxCheckIDCard.isSelected() ? "true" : "false");

				JSONObject service = new JSONObject();
				service.put("title", textFieldTitle.getText());
				service.put("time", textFieldTime.getText());
				service.put("price", textFieldPrice.getText());
				service.put("timeperweek", textFieldTimePerWeek.getText());
				service.put("content", textFieldContent.getText());

				JSONArray tips = new JSONArray();
				if (checkBoxTip1.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "1");
					tips.add(t);
				}
				if (checkBoxTip2.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "2");
					tips.add(t);
				}
				if (checkBoxTip4.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "4");
					tips.add(t);
				}
				if (checkBoxTip8.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "8");
					tips.add(t);
				}
				if (checkBoxTip16.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "16");
					tips.add(t);
				}
				if (checkBoxTip32.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "32");
					tips.add(t);
				}
				if (checkBoxTip64.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "64");
					tips.add(t);
				}
				if (checkBoxTip128.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "128");
					tips.add(t);
				}
				if (checkBoxTip256.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "256");
					tips.add(t);
				}
				if (checkBoxTip512.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "512");
					tips.add(t);
				}
				if (checkBoxTip1024.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "1024");
					tips.add(t);
				}
				if (checkBoxTip2048.isSelected()) {
					JSONObject t = new JSONObject();
					t.put("id", "2048");
					tips.add(t);
				}
				service.put("tips", tips);

				object.put("service", service);
				object.put("workExperience", weList);
				object.put("studyExperience", seList);

				JSONObject toSend = new JSONObject();
				toSend.put("style", "manager");
				toSend.put("method", "editTeacher");
				toSend.put("mid", ManagerData.MID);
				toSend.put("username", textFieldUsername.getText());
				toSend.put("teacher", object);
				String json = MsgUtil.sendMsg(toSend.toString());
				Map<String, String> m = Json.getMap(json);
				if (m.get("state").equals("success")) {
					JOptionPane.showMessageDialog(mFrame, "修改成功");
					mFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mFrame, m.get("msg"));
				}
			}
		});

		mFrame.add(btnSave);

		mFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mFrame.dispose();
			}
		});

	}

	public void start() {
		mFrame.setVisible(true);
	}

	@Override
	public void onSESave(JSONObject data) {
		seList.add(data);
		textAreaStudyExp.setText(seList.toString());
		// System.out.println(data);
		// System.out.println(seList);
	}

	@Override
	public void onWESave(JSONObject data) {
		weList.add(data);
		textAreaWorkExp.setText(weList.toString());
		// System.out.println(data);
		// System.out.println(weList);
	}
}
