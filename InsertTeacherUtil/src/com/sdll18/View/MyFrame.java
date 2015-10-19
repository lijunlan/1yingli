package com.sdll18.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.sdll18.Data.ManagerData;
import com.sdll18.Util.MsgUtil;
import com.sdll18.Util.SuperMap;

public class MyFrame {

	private JFrame mFrame;

	public MyFrame() {

		mFrame = new JFrame("InsertTeacher");
		// mFrame.setResizable(false);
		mFrame.setSize(350, 220);
		mFrame.setTitle("InsertTeacher");
		mFrame.setLayout(null);
		mFrame.setLocationRelativeTo(null);

		JButton btnRegister = new JButton("注册");
		JButton btnInsertInfo = new JButton("登录并录入信息");
		JButton btnEditInfo = new JButton("编辑导师信息");

		btnRegister.setBounds(50, 120, 120, 20);
		btnInsertInfo.setBounds(180, 120, 120, 20);
		btnEditInfo.setBounds(50, 150, 120, 20);

		btnInsertInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateTeacherFrame frame = new CreateTeacherFrame();
				frame.start();
			}
		});

		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = RegisterFrame.getFrame();
				frame.setVisible(true);
				frame.requestFocus();
			}
		});

		btnEditInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditTeacherFrame frame = new EditTeacherFrame();
				frame.start();
			}
		});

		mFrame.add(btnRegister);
		mFrame.add(btnInsertInfo);
		mFrame.add(btnEditInfo);

		mFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SuperMap map = new SuperMap();
				map.put("style", "manager");
				map.put("method", "logout");
				map.put("mid", ManagerData.MID);
				MsgUtil.sendMsg(map.finishByJson());
				System.exit(0);
			}
		});
	}

	public void start() {
		mFrame.setVisible(true);
	}
}
