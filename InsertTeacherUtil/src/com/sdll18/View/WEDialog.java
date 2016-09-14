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

public class WEDialog {

	public interface WEDialogListener {

		void onWESave(JSONObject data);
	}

	private JDialog dialog;

	public WEDialog(JFrame frame, final WEDialogListener listener) {
		dialog = new JDialog(frame);
		dialog.setVisible(false);
		dialog.setTitle("添加工作经历");
		dialog.setSize(450, 600);
		dialog.setLayout(null);
		dialog.setLocationRelativeTo(null);

		JLabel labelCompanyName = new JLabel("公司名称");
		JLabel labelPosition = new JLabel("职位");
		JLabel labelDescription = new JLabel("工作经历");
		JLabel labelStartTime = new JLabel("开始时间e.g.2001,9");
		JLabel labelEndTime = new JLabel("结束时间e.g.2010,10 or 至今");

		labelCompanyName.setBounds(10, 10, 400, 20);
		labelPosition.setBounds(10, 70, 400, 20);
		labelStartTime.setBounds(10, 130, 400, 20);
		labelEndTime.setBounds(10, 190, 400, 20);
		labelDescription.setBounds(10, 250, 400, 20);

		dialog.add(labelCompanyName);
		dialog.add(labelPosition);
		dialog.add(labelDescription);
		dialog.add(labelStartTime);
		dialog.add(labelEndTime);

		final JTextField textFieldCompanyName = new JTextField();
		final JTextField textFieldPosition = new JTextField();
		final JTextField textFieldEndTime = new JTextField();
		final JTextField textFielStartTime = new JTextField();
		final JTextArea textAreaDescription = new JTextArea();
		JScrollPane js = new JScrollPane(textAreaDescription);

		textFieldCompanyName.setBounds(10, 40, 400, 20);
		textFieldPosition.setBounds(10, 100, 400, 20);
		textFielStartTime.setBounds(10, 160, 400, 20);
		textFieldEndTime.setBounds(10, 220, 400, 20);
		js.setBounds(10, 280, 400, 200);

		textAreaDescription.setWrapStyleWord(true);
		textAreaDescription.setLineWrap(true);

		dialog.add(textFieldCompanyName);
		dialog.add(textFieldPosition);
		dialog.add(js);
		dialog.add(textFieldEndTime);
		dialog.add(textFielStartTime);

		JButton btnSave = new JButton("保存");

		btnSave.setBounds(450 / 2 - 100 / 2, 520 - 40 / 2, 100, 40);

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JSONObject object = new JSONObject();
				object.put("companyName", textFieldCompanyName.getText());
				object.put("position", textFieldPosition.getText());
				object.put("startTime", textFielStartTime.getText());
				object.put("endTime", textFieldEndTime.getText());
				object.put("description", textAreaDescription.getText());
				listener.onWESave(object);
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
