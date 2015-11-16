package com.sdll18.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.sdll18.Data.ManagerData;
import com.sdll18.Util.Json;
import com.sdll18.Util.MsgUtil;
import com.sdll18.Util.RSAUtil;
import com.sdll18.Util.SuperMap;

public class RegisterFrame {

	private static JFrame mFrame;

	public static JFrame getFrame() {
		if (mFrame == null) {
			mFrame = createFrame();
		}
		return mFrame;

	}

	private static JFrame createFrame() {
		final JFrame frame = new JFrame("注册");
		frame.setVisible(false);
		frame.setSize(300, 220);
		frame.setTitle("Register");
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);

		JButton btnRegister = new JButton("注册");
		JLabel labelNickname = new JLabel("昵称");
		JLabel labelUsername = new JLabel("用户名");
		JLabel labelPassword = new JLabel("密码");
		final JTextField textFieldUsername = new JTextField();
		final JTextField textFieldPassword = new JTextField();
		final JTextField textFieldNickname = new JTextField();

		labelNickname.setBounds(50, 20, 50, 20);
		labelUsername.setBounds(50, 50, 50, 20);
		labelPassword.setBounds(50, 80, 50, 20);
		textFieldNickname.setBounds(120, 20, 150, 20);
		textFieldUsername.setBounds(120, 50, 150, 20);
		textFieldPassword.setBounds(120, 80, 150, 20);
		btnRegister.setBounds(50, 120, 50, 20);

		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = textFieldUsername.getText();
				String password = textFieldPassword.getText();
				String nickName = textFieldNickname.getText();
				password = RSAUtil.encryptStr(password);
				SuperMap map = new SuperMap();
				map.put("style","manager");
				map.put("method", "registerUser");
				map.put("mid", ManagerData.MID);
				map.put("username", username);
				map.put("password", password);
				map.put("nickName", nickName);
				String json = MsgUtil.sendMsg(map.finishByJson());
				Map<String, String> m = Json.getMap(json);
				if (m.get("state").equals("success")) {
					JOptionPane.showMessageDialog(frame, "注册成功");
				} else {
					JOptionPane.showMessageDialog(frame, m.get("msg"));
				}
			}
		});
		frame.add(textFieldNickname);
		frame.add(labelNickname);
		frame.add(textFieldPassword);
		frame.add(textFieldUsername);
		frame.add(labelPassword);
		frame.add(labelUsername);
		frame.add(btnRegister);
		return frame;
	}

}
