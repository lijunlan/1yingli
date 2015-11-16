package com.sdll18.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.json.JSONObject;

public class SEDialog {

	public interface SEDialogListener {
		void onSESave(JSONObject data);
	}

	private JDialog dialog;

	public SEDialog(JFrame frame, final SEDialogListener listener) {
		dialog = new JDialog(frame);
		dialog.setVisible(false);
		dialog.setTitle("添加教育经历");
		dialog.setSize(450, 650);
		dialog.setLayout(null);
		dialog.setLocationRelativeTo(null);

		JLabel labelSchoolName = new JLabel("学校名称");
		JLabel labelDegree = new JLabel("学位");
		JLabel labelDescription = new JLabel("教育经历");
		JLabel labelMajor = new JLabel("专业");
		JLabel labelStartTime = new JLabel("开始时间e.g.2001,9");
		JLabel labelEndTime = new JLabel("结束时间e.g.2010,10 or 至今");

		labelSchoolName.setBounds(10, 10, 400, 20);
		labelDegree.setBounds(10, 70, 400, 20);
		labelMajor.setBounds(10, 130, 400, 20);
		labelStartTime.setBounds(10, 190, 400, 20);
		labelEndTime.setBounds(10, 250, 400, 20);
		labelDescription.setBounds(10, 310, 400, 20);

		dialog.add(labelSchoolName);
		dialog.add(labelDegree);
		dialog.add(labelMajor);
		dialog.add(labelDescription);
		dialog.add(labelStartTime);
		dialog.add(labelEndTime);

		final JTextField textFieldSchoolName = new JTextField();
		final JTextField textFieldDegree = new JTextField();
		final JTextField textFieldEndTime = new JTextField();
		final JTextField textFieldMajor = new JTextField();
		final JTextField textFielStartTime = new JTextField();
		final JTextArea textAreaDescription = new JTextArea();
		JScrollPane js = new JScrollPane(textAreaDescription);

		textFieldSchoolName.setBounds(10, 40, 400, 20);
		textFieldDegree.setBounds(10, 100, 400, 20);
		textFieldMajor.setBounds(10, 160, 400, 20);
		textFielStartTime.setBounds(10, 220, 400, 20);
		textFieldEndTime.setBounds(10, 280, 400, 20);
		js.setBounds(10, 340, 400, 200);

		textAreaDescription.setWrapStyleWord(true);
		textAreaDescription.setLineWrap(true);

		dialog.add(textFieldSchoolName);
		dialog.add(textFieldDegree);
		dialog.add(js);
		dialog.add(textFieldEndTime);
		dialog.add(textFieldMajor);
		dialog.add(textFielStartTime);

		JButton btnSave = new JButton("保存");

		btnSave.setBounds(450 / 2 - 100 / 2, 600 - 40 / 2, 100, 40);

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject object = new JSONObject();
				object.put("schoolName", textFieldSchoolName.getText());
				object.put("degree", textFieldDegree.getText());
				object.put("major", textFieldMajor.getText());
				object.put("startTime", textFielStartTime.getText());
				object.put("endTime", textFieldEndTime.getText());
				object.put("description", textAreaDescription.getText());
				listener.onSESave(object);
				dialog.dispose();
			}
		});

		dialog.add(btnSave);

		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dialog.dispose();
			}
		});
	}
	
	public void start() {
		dialog.setVisible(true);
	}
}
