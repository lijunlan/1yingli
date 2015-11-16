package com.sdll18.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.sdll18.Data.ManagerData;
import com.sdll18.Util.Json;
import com.sdll18.Util.MsgUtil;
import com.sdll18.Util.RSAUtil;
import com.sdll18.Util.SuperMap;

public class LoginFrame {

	private JFrame mFrame;

	public LoginFrame() {
		mFrame = new JFrame("Manager Login");
		mFrame.setSize(300, 220);
		mFrame.setTitle("Manager Login");
		mFrame.setLayout(null);
		mFrame.setLocationRelativeTo(null);

		final JButton btnLogin = new JButton("登录");
		JLabel labelUsername = new JLabel("用户名");
		JLabel labelPassword = new JLabel("密码");
		final JTextField textFieldUsername = new JTextField();
		final JTextField textFieldPassword = new JTextField();

		labelUsername.setBounds(50, 50, 50, 20);
		labelPassword.setBounds(50, 80, 50, 20);
		textFieldUsername.setBounds(120, 50, 150, 20);
		textFieldPassword.setBounds(120, 80, 150, 20);
		btnLogin.setBounds(50, 120, 100, 20);

		textFieldPassword.registerKeyboardAction(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnLogin.doClick();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);

		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = textFieldUsername.getText();
				String password = textFieldPassword.getText();
				password = RSAUtil.encryptStr(password);
				SuperMap map = new SuperMap();
				map.put("style", "manager");
				map.put("method", "login");
				map.put("mid", ManagerData.MID);
				map.put("username", username);
				map.put("password", password);
				String json = MsgUtil.sendMsg(map.finishByJson());
				Map<String, String> m = Json.getMap(json);
				if (m.get("state").equals("success")) {
					ManagerData.MID = m.get("mid");
					JOptionPane.showMessageDialog(mFrame, "登录成功");
					MyFrame frame = new MyFrame();
					frame.start();
					mFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mFrame,  m.get("msg"));
				}
			}
		});

		mFrame.add(labelUsername);
		mFrame.add(textFieldUsername);
		mFrame.add(labelPassword);
		mFrame.add(textFieldPassword);
		mFrame.add(btnLogin);

		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void start() {
		mFrame.setVisible(true);
	}
}
