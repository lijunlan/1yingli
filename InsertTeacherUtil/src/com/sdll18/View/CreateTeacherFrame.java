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

import com.sdll18.Data.ManagerData;
import com.sdll18.Util.Json;
import com.sdll18.Util.MsgUtil;
import com.sdll18.Util.UploadUtil;
import com.sdll18.View.SEDialog.SEDialogListener;
import com.sdll18.View.WEDialog.WEDialogListener;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CreateTeacherFrame implements SEDialogListener, WEDialogListener {

	private JFrame mFrame;

	private JSONArray weList = new JSONArray();

	private JSONArray seList = new JSONArray();

	private JTextArea textAreaWorkExp;

	private JTextArea textAreaStudyExp;

	public CreateTeacherFrame() {
		mFrame = new JFrame("InsertTeacher");
		// mFrame.setResizable(false);
		mFrame.setSize(1300, 700);
		mFrame.setTitle("InsertTeacher");
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

		final JTextField textFieldShowWeight1 = new JTextField();
		final JTextField textFieldShowWeight2 = new JTextField();
		final JTextField textFieldShowWeight4 = new JTextField();
		final JTextField textFieldShowWeight8 = new JTextField();
		final JTextField textFieldShowWeight16 = new JTextField();
		
		final JTextField textFieldSimpleInfo = new JTextField();
		final JTextField textFieldName = new JTextField();
		final JTextField textFieldPhone = new JTextField();
		final JTextField textFieldAddress = new JTextField();
		final JTextField textFieldEmail = new JTextField();
		final JTextField textFieldIcon = new JTextField();
		final JTextField textFieldTitle = new JTextField();
		final JTextField textFieldTime = new JTextField();
		final JTextField textFieldPrice = new JTextField();
		final JTextField textFieldTimePerWeek = new JTextField();
		final JTextArea textFieldContent = new JTextArea();
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

		mFrame.add(textFieldSimpleInfo);
		mFrame.add(textFieldShowWeight1);
		mFrame.add(textFieldShowWeight2);
		mFrame.add(textFieldShowWeight4);
		mFrame.add(textFieldShowWeight8);
		mFrame.add(textFieldShowWeight16);
		
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

		final JTextField textFieldUsername = new JTextField();
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

		final JTextArea textAreaIntroduce = new JTextArea();
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
				WEDialog dialog = new WEDialog(mFrame, CreateTeacherFrame.this);
				dialog.start();
			}
		});

		btnAddStudyExp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SEDialog dialog = new SEDialog(mFrame, CreateTeacherFrame.this);
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
				toSend.put("method", "createTeacher");
				toSend.put("mid", ManagerData.MID);
				toSend.put("username", textFieldUsername.getText());
				toSend.put("teacher", object);
				String json = MsgUtil.sendMsg(toSend.toString());
				Map<String, String> m = Json.getMap(json);
				if (m.get("state").equals("success")) {
					JOptionPane.showMessageDialog(mFrame, "插入成功");
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
