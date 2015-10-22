//
//  EditUserInfoViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EditUserInfoViewController.h"
#import "EditHeadTableViewCell.h"
#import "EditUserInfoTableViewCell.h"
#import "editUsersNormalTableViewCell.h"
#import "EditIntroTableViewCell.h"
#import "EditConnectionTableViewCell.h"
#import "ChangePhoneViewController.h"
#import "ChangeEmailViewController.h"
#import "LoginViewController.h"
#import "UserInfoModel.h"

@interface EditUserInfoViewController ()<EditInterface, EditIntroInterface, editNormalInterface, changePhoneInterface, changeEmailInterface, editHeaderInterface, ASIHTTPRequestDelegate, UIAlertViewDelegate, UIActionSheetDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate>

@property (nonatomic, strong) UserInfoModel *personalInfo;
@property (nonatomic, strong) UserInfoModel *hasChangedInfo;
@property (nonatomic, strong) EditHeadTableViewCell *editHeadCell;
@property (nonatomic, strong) UIImage *currentI;

@end

@implementation EditUserInfoViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.personalInfo = [[UserInfoModel alloc] init];
        self.hasChangedInfo = [[UserInfoModel alloc] init];
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 50, 40, 100, 30)];
    titleL.text = @"个人资料";
    titleL.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleL.textAlignment = NSTextAlignmentCenter;
    titleL.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleL];
    
    UIButton *editToBackBT = [UIButton buttonWithType:UIButtonTypeCustom];
    editToBackBT.frame = CGRectMake(20, titleL.frame.origin.y, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [editToBackBT setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    [self.view addSubview:editToBackBT];
    
    [editToBackBT addTarget:self action:@selector(editBackAction:) forControlEvents:UIControlEventTouchUpInside];
    
    //保存
    UIButton *editBT = [UIButton buttonWithType:UIButtonTypeCustom];
    editBT.frame = CGRectMake(WIDTH - 50 - 20, titleL.frame.origin.y, 50, 28);
    [editBT setBackgroundColor:[UIColor colorWithRed:104 / 255.0 green:201 / 255.0 blue:250 / 255.0 alpha:1.0]];
    [editBT setTitle:@"保 存" forState:UIControlStateNormal];
    editBT.layer.masksToBounds = YES;
    editBT.layer.cornerRadius = 5.0f;
    [editBT setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.view addSubview:editBT];
    
    [editBT addTarget:self action:@selector(saveButton:) forControlEvents:UIControlEventTouchUpInside];
    
    [self createSubviews];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self getDetailUserInfo];
    } else {
    
        
    }
}

-(void)editBackAction:(UIButton *)button
{
    [self dismissViewControllerAnimated:YES completion:^{
        
        
    }];
}

-(void)saveButton:(UIButton *)button
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"确定要修改个人信息？" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确认", nil];
    [self.view addSubview:alert];
    
    alert.delegate = self;
    
    [alert show];
}

#pragma mark -- alertViewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"确定要修改个人信息？"]) {
        
        if (buttonIndex == 1) {
            
            [self changePersonalInfo];
        }
    } else if ([alertView.message isEqualToString:@"登录超时，请重新登录"]) {
    
        if (buttonIndex == 0) {
            
            [self dismissViewControllerAnimated:YES completion:^{
                
                
            }];
        } else {
        
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            loginVC.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
            
            [self presentViewController:loginVC animated:YES completion:^{
                
                
            }];
        }
    }
}

#pragma mark -- createSubviews
-(void)createSubviews
{
    UIScrollView *editUserInfoSV = [[UIScrollView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT)];
    [self.view addSubview:editUserInfoSV];
    
    editUserInfoSV.contentSize = CGSizeMake(WIDTH, 100 * 2 + 60 * 7 + 60);
    
    self.editUserInfoTV = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, WIDTH, 100 * 2 + 60 * 7) style:UITableViewStylePlain];
    self.editUserInfoTV.bounces = YES;
    self.editUserInfoTV.showsVerticalScrollIndicator = NO;
    [editUserInfoSV addSubview:_editUserInfoTV];
    
    _editUserInfoTV.dataSource = self;
    _editUserInfoTV.delegate = self;
    
    _editUserInfoTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    UIButton *logoutBT = [UIButton buttonWithType:UIButtonTypeCustom];
    logoutBT.frame = CGRectMake(WIDTH / 2.0 - 50, self.editUserInfoTV.frame.origin.y + self.editUserInfoTV.frame.size.height + 10, 100, 40);
    [logoutBT setBackgroundColor:[UIColor whiteColor]];
    [logoutBT setTitle:@"退出登录" forState:UIControlStateNormal];
    [logoutBT setTitleColor:[UIColor colorWithRed:72 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0] forState:UIControlStateNormal];
    [editUserInfoSV addSubview:logoutBT];
    
    [logoutBT addTarget:self action:@selector(logoutAction:) forControlEvents:UIControlEventTouchUpInside];
    
    //结束编辑
    UIGestureRecognizer *resignKeyboard = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(backgroundTapped:)];
    [self.view addGestureRecognizer:resignKeyboard];
}

-(void)logoutAction:(UIButton *)button
{
    [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
    
    [self dismissViewControllerAnimated:YES completion:^{
        
        
    }];
}

//键盘弹回，退出编辑模式
-(void)backgroundTapped:(UITapGestureRecognizer *)tap
{
    [self.view endEditing:YES];
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 9;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 8) {
        
        return 100;
    } else if (indexPath.row == 0) {
    
        return 100;
    }
    
    return 60;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *editHeadCellIdentifier = @"editHeadCell";
    static NSString *editUsersCellIdentifier = @"editUserCell";
    static NSString *editNormalCellIdentifier = @"editNormalCell";
    static NSString *editIntroCellIdentifier = @"editIntroCell";
    static NSString *editConnectCellIdentifier = @"editConnectCell";
    
    self.editHeadCell = [tableView dequeueReusableCellWithIdentifier:editHeadCellIdentifier];
    EditUserInfoTableViewCell *editUsersCell = [tableView dequeueReusableCellWithIdentifier:editUsersCellIdentifier];
    editUsersNormalTableViewCell *editNormalCell = [tableView dequeueReusableCellWithIdentifier:editNormalCellIdentifier];
    EditIntroTableViewCell *editIntroCell = [tableView dequeueReusableCellWithIdentifier:editIntroCellIdentifier];
    EditConnectionTableViewCell *editConnectCell = [tableView dequeueReusableCellWithIdentifier:editConnectCellIdentifier];
    
    if (indexPath.row == 0) {
        
        if (self.editHeadCell == nil) {
            
            self.editHeadCell = [[EditHeadTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:editHeadCellIdentifier];
            self.editHeadCell.myDelegate = self;
            self.editHeadCell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        
    } else if (indexPath.row == 3 || indexPath.row == 4) {
    
        if (editUsersCell == nil) {
            
            editUsersCell = [[EditUserInfoTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:editUsersCellIdentifier];
            editUsersCell.selectionStyle = UITableViewCellSelectionStyleNone;
            editUsersCell.myDelegate = self;
        }
    } else if (indexPath.row == 6 || indexPath.row == 7) {
    
        if (editConnectCell == nil) {
            
            editConnectCell = [[EditConnectionTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:editConnectCellIdentifier];
            editConnectCell.selectionStyle = UITableViewCellSelectionStyleNone;
            
        }
    } else if (indexPath.row == 1 || indexPath.row == 2 || indexPath.row == 5) {
    
        if (editNormalCell == nil) {
            
            editNormalCell = [[editUsersNormalTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:editNormalCellIdentifier];
            editNormalCell.selectionStyle = UITableViewCellSelectionStyleNone;
            editNormalCell.myDelegate = self;
        }
    } else {
    
        if (editIntroCell == nil) {
            
            editIntroCell = [[EditIntroTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:editIntroCellIdentifier];
            editIntroCell.selectionStyle = UITableViewCellSelectionStyleNone;
            editIntroCell.myDelegate = self;
        }
    }
    
    if (indexPath.row == 0) { //头像
        
        if (self.myData == nil) {
            
            [self.editHeadCell.headerIV sd_setImageWithURL:[NSURL URLWithString:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"iconUrl"]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        } else {
        
            _editHeadCell.downloadDic = (NSMutableDictionary *)[NSJSONSerialization JSONObjectWithData:self.myData options:NSJSONReadingMutableContainers error:nil];
        }
        
        return _editHeadCell;
    } else if (indexPath.row == 8) { //简介
    
        if (_personalInfo.resume.length == 0) {
            
            editIntroCell.introTV.text = @"这个人很懒，什么也没有留下";
            _hasChangedInfo.resume = @"这个人很懒，什么也没有留下";
        } else {
        
            editIntroCell.introTV.text = _personalInfo.resume;
            _hasChangedInfo.resume = _personalInfo.resume;
        }
        
        return editIntroCell;
    } else if (indexPath.row == 1 || indexPath.row == 2 || indexPath.row == 5){
        
        if (indexPath.row == 1) { //信息
            
            editNormalCell.userNormL.text = @"昵称";
            editNormalCell.userNormTF.text = _personalInfo.nickName;
            _hasChangedInfo.nickName = _personalInfo.nickName;
            editNormalCell.userNormTF.tag = 10030;
        } else if (indexPath.row == 2) {
        
            editNormalCell.userNormL.text = @"真实姓名";
            editNormalCell.userNormTF.text = _personalInfo.name;
            _hasChangedInfo.name = _personalInfo.name;
            editNormalCell.userNormTF.tag = 10031;
            
        } else {
        
            editNormalCell.userNormL.text = @"常驻地";
            editNormalCell.userNormTF.text = _personalInfo.address;
            _hasChangedInfo.address = _personalInfo.address;
            editNormalCell.userNormTF.tag = 10034;
            
        }
        
        return editNormalCell;
    } else if (indexPath.row == 3 || indexPath.row == 4) {
    
        if (indexPath.row == 3) {
            
            editUsersCell.userInfoL.text = @"手机号码";
            editUsersCell.userInfoTF.text = _personalInfo.phone;
            _hasChangedInfo.phone = _personalInfo.phone;
            editUsersCell.telOrEmBT.tag = 10032;
            
        } else {
            
            editUsersCell.userInfoL.text = @"邮箱";
            editUsersCell.userInfoTF.text = _personalInfo.email;
            _hasChangedInfo.email = _personalInfo.email;
            editUsersCell.telOrEmBT.tag = 10033;
            
        }
        
        return editUsersCell;
    } else { //微信 微博
    
        if (indexPath.row == 6) {
            
            editConnectCell.connectL.text = @"微信";
        } else {
        
            editConnectCell.connectL.text = @"微博";
        }
        return editConnectCell;
    }
}

#pragma mark -- tableviewDidSelected
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
}

#pragma mark -- viewWillAppear && viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.tabBarController.tabBar.hidden = NO;
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
    } else {
        
    }
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.tabBarController.tabBar.hidden = NO;
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
    } else {
        
    }
}

#pragma mark -- editNormalDelegate
//开始编辑时
-(void)beginEditText
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.view.frame = CGRectMake(0, -47, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

//结束编辑时
-(void)endEditText:(UITextField *)text
{
    if (text.tag == 10030) {
        
        self.hasChangedInfo.nickName = text.text;
    } else if (text.tag == 10031) {
        
        self.hasChangedInfo.name = text.text;
    } else if (text.tag == 10034) {
        
        self.hasChangedInfo.address = text.text;
    }
    
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.view.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

#pragma mark -- EditUserInfoDelegate
//改变手机
-(void)changeTelNum
{
    ChangePhoneViewController *changeVC = [[ChangePhoneViewController alloc] init];
    
    changeVC.userIDforChange = [[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"];
    changeVC.myDelegate = self;
    
    [self presentViewController:changeVC animated:YES completion:^{
        
        
    }];
}

//修改邮箱
-(void)changeEmail
{
    ChangeEmailViewController *changeVC = [[ChangeEmailViewController alloc] init];
    changeVC.userIDforChangeEm = [[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"];
    changeVC.myDelegate = self;
    
    [self presentViewController:changeVC animated:YES completion:^{
        
        
    }];
}

#pragma mark -- changePhoneDelegate
-(void)changeSuccess:(NSString *)phoneNum
{
    _personalInfo.phone = phoneNum;
    _hasChangedInfo.phone = phoneNum;
    [self.editUserInfoTV reloadData];
    
    NSMutableDictionary *tmpDic = [NSMutableDictionary dictionaryWithDictionary:[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"]];
    NSString *username = [tmpDic objectForKey:@"number"];
    
    if ([self isValidateMobile:username]) {
        
        [tmpDic setObject:phoneNum forKey:@"number"];
        [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithDictionary:tmpDic] forKey:@"userInfo"];
    }
}

#pragma mark -- changeEmailDelegate
-(void)changeEmailSuccess:(NSString *)email
{
    _personalInfo.email = email;
    _hasChangedInfo.email = email;
    [self.editUserInfoTV reloadData];
    
    NSMutableDictionary *tmpDic = [NSMutableDictionary dictionaryWithDictionary:[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"]];
    NSString *username = [tmpDic objectForKey:@"number"];
    
    if ([self isValidateEmail:username]) {
        
        [tmpDic setObject:email forKey:@"number"];
        [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithDictionary:tmpDic] forKey:@"userInfo"];
    }
}

#pragma mark -- EditIntroDelegate
//开始编辑时
-(void)beginEdit
{
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.view.frame = CGRectMake(0, -217, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

//结束编辑时
-(void)endEdit:(NSString *)intro
{
    _hasChangedInfo.resume = intro;
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:0.3f];
    
    self.view.frame = CGRectMake(0, 0, WIDTH, HEIGHT);
    
    [UIView commitAnimations];
}

#pragma mark -- 获取数据
//获取个人详细信息
-(void)getDetailUserInfo
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForPersonalInfo:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"]] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"error"]) {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
                [alert show];
            }
        } else {
            
            self.personalInfo.resume = [dic objectForKey:@"resume"];
            self.personalInfo.phone = [dic objectForKey:@"phone"];
            self.personalInfo.email = [dic objectForKey:@"email"];
            self.personalInfo.nickName = [dic objectForKey:@"nickName"];
            self.personalInfo.address = [dic objectForKey:@"address"];
            self.personalInfo.name = [dic objectForKey:@"name"];
            
            [self.editUserInfoTV reloadData];
        }
    }];
}

//修改个人详细信息
-(void)changePersonalInfo
{
    [self uploadPhoto];
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForChangeInfo:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withNickname:_hasChangedInfo.nickName withName:_hasChangedInfo.name withAddress:_hasChangedInfo.address withEmail:_hasChangedInfo.email withResume:_hasChangedInfo.resume] connectBlock:^(id data) {
        
        NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        NSString *state = [dic objectForKey:@"state"];
        
        if ([state isEqualToString:@"success"]) {
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"温馨提示" message:@"修改成功" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [self.view addSubview:alert];
            
            [alert show];
        } else {
        
            NSString *msg = [dic objectForKey:@"msg"];
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:msg delegate:self cancelButtonTitle:@"query" otherButtonTitles:nil];
            [self.view addSubview:alert];
            
            [alert show];
        }
    }];
}

#pragma mark -- 保存图片
-(void)uploadPhoto
{
    if (self.myData == nil) {
        
    } else {
    
        [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForSavePhoto:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"] withiconUrl:[((NSMutableDictionary *)[NSJSONSerialization JSONObjectWithData:self.myData options:NSJSONReadingMutableContainers error:nil]) objectForKey:@"url"]] connectBlock:^(id data) {
            
            NSDictionary *dic = (NSDictionary *)[NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
            
            if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
                
                NSMutableDictionary *changeUserDic = [NSMutableDictionary dictionaryWithDictionary:[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"]];
                
                NSDictionary *urlDic = [NSJSONSerialization JSONObjectWithData:self.myData options:NSJSONReadingMutableContainers error:nil];
                NSString *iconPhoto = [urlDic objectForKey:@"url"];
                [changeUserDic setObject:iconPhoto forKey:@"iconUrl"];
                
                [[NSUserDefaults standardUserDefaults] setObject:[NSDictionary dictionaryWithDictionary:changeUserDic] forKey:@"userInfo"];
            } else {
                
                if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                    
                    [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                    
                    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
                    [alert show];
                } else {
                    
                    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"头像上传失败" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                    
                    [alert show];
                }
            }
        }];
    }
}

#pragma mark -- 有效手机号和邮箱
//邮箱验证
-(BOOL)isValidateEmail:(NSString *)email
{
    NSString *emailRegex = @"[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
    
    return [emailTest evaluateWithObject:email];
}

//手机号验证
-(BOOL)isValidateMobile:(NSString *)mobile
{
    //手机号以13， 15，18开头，八个 \d 数字字符
    NSString *phoneRegex = @"^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
    NSPredicate *phoneTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",phoneRegex];
    
    //    NSLog(@"phoneTest is %@",phoneTest);
    return [phoneTest evaluateWithObject:mobile];
}

// *********************************************************************************
// *********************************************************************************
#pragma mark -- 相机 & 相册
-(void)chooseImage
{
    UIActionSheet *sheet;
    
    // 判断是否支持相机
    
    if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        
        sheet = [[UIActionSheet alloc] initWithTitle:@"选择" delegate:self cancelButtonTitle:nil destructiveButtonTitle:@"取消" otherButtonTitles:@"拍照",@"从相册选择", nil];
    }
    
    else {
        
        sheet = [[UIActionSheet alloc] initWithTitle:@"选择" delegate:self cancelButtonTitle:nil destructiveButtonTitle:@"取消" otherButtonTitles:@"从相册选择", nil];
        
    }
    sheet.tag = 255;
    
    [sheet showInView:self.view];
}

#pragma mark -- 实现actionSheetDelegate
-(void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (actionSheet.tag == 255) {
        
        NSUInteger sourceType = 0;
        
        // 判断是否支持相机
        if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
            
            switch (buttonIndex) {
                case 0:
                    // 取消
                    return;
                case 1:
                    // 相机
                    sourceType = UIImagePickerControllerSourceTypeCamera;
                    break;
                    
                case 2:
                    // 相册
                    sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
                    break;
            }
        } else {
            
            if (buttonIndex == 0) {
                
                return;
            } else {
                
                sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
            }
        }
        // 跳转到相机或相册页面
        UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
        
        imagePickerController.delegate = self;
        
        imagePickerController.allowsEditing = YES;
        
        imagePickerController.sourceType = sourceType;
        
        [self presentViewController:imagePickerController animated:YES completion:^{
        
        }];
    }
}

#pragma mark -- imagePickerDelegate
-(void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    [picker dismissViewControllerAnimated:YES completion:^{
    
    }];
    
    UIImage *image = [info objectForKey:UIImagePickerControllerOriginalImage];
    /* 此处info 有六个值
     * UIImagePickerControllerMediaType; // an NSString UTTypeImage)
     * UIImagePickerControllerOriginalImage;  // a UIImage 原始图片
     * UIImagePickerControllerEditedImage;    // a UIImage 裁剪后图片
     * UIImagePickerControllerCropRect;       // an NSValue (CGRect)
     * UIImagePickerControllerMediaURL;       // an NSURL
     * UIImagePickerControllerReferenceURL    // an NSURL that references an asset in the AssetsLibrary framework
     * UIImagePickerControllerMediaMetadata    // an NSDictionary containing metadata from a captured photo
     */
    // 保存图片至本地，方法见下文
    [AFNConnect createDataForSavePhoto:image withName:@"currentImage.png"];
    
//    NSString *fullPath = [[[NSHomeDirectory() stringByAppendingPathComponent:@"Documents"] stringByAppendingPathComponent:@"savePhoto"] stringByAppendingPathComponent:@"currentImage.png"];
    
    //isFullScreen = NO;
    //上传
    [self uploadImage:[AFNConnect createDataForSavePhoto:image withName:@"currentImage.png"]];
//    self.imageView.tag = 100;
}

//取消
-(void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    [self dismissViewControllerAnimated:YES completion:^{
        
        
    }];
}

#pragma mark -- 上传图片，使用ASIhttpRequest类库实现
-(void)uploadImage:(NSString *)path
{
    ASIFormDataRequest *requestReport = [[ASIFormDataRequest alloc] initWithURL:[NSURL URLWithString:[@"http://service.1yingli.cn/yiyingliService/upimage" stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]]];
    
    [requestReport setFile:path forKey:@"picturePath"];
    
    [requestReport buildPostBody];
    
    requestReport.delegate = self;
    
    [requestReport setCompletionBlock:^{
        
        UIAlertView *successAV = [[UIAlertView alloc] initWithTitle:@"提示" message:@"头像上传成功" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [self.view addSubview:successAV];
        
        [successAV show];
    }];
    
    [requestReport setFailedBlock:^{
        
        UIAlertView *failAV = [[UIAlertView alloc] initWithTitle:@"提示" message:@"头像上传失败" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [self.view addSubview:failAV];
        
        [failAV show];
    }];
    
    [requestReport startAsynchronous];
}

#pragma mark -- ASIHttpRequestDelegate
-(void)requestStarted:(ASIHTTPRequest *)request
{
    self.myData = [NSMutableData data];
}

-(void)request:(ASIHTTPRequest *)request didReceiveData:(NSData *)data
{
    [self.myData appendData:data];
}

-(void)requestFinished:(ASIHTTPRequest *)request
{
//    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:self.myData options:NSJSONReadingMutableContainers error:nil];
//    
//    NSString *picUrl = [[dic objectForKey:@"url"] substringWithRange:NSMakeRange(0, ((NSString *)[dic objectForKey:@"url"]).length - 6)];
//    NSLog(@"%@",picUrl);
//    
//    self.editHeadCell.downloadDic = (NSMutableDictionary *)dic;
    [self.editUserInfoTV reloadData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
