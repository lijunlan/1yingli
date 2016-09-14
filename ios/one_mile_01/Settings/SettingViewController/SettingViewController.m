
//  SettingViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "SettingViewController.h"
#import "EditUserInfoViewController.h"
#import "LoginViewController.h"
#import "YourTutorsViewController.h"

@interface SettingViewController ()<UIAlertViewDelegate>

@end

@implementation SettingViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.isFirst = YES;
    
    self.view.backgroundColor = [UIColor whiteColor];
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 10, 40,25, 30)];
    
    
    titleLabel.text = @"我";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    UIButton *editUserInfoBT = [UIButton buttonWithType:UIButtonTypeCustom];
    editUserInfoBT.frame = CGRectMake(WIDTH - 55, titleLabel.frame.origin.y - 5, titleLabel.frame.size.height + 5, titleLabel.frame.size.height + 7);
    [editUserInfoBT setBackgroundImage:[UIImage imageNamed:@"settingIcon.png"] forState:UIControlStateNormal];
    [self.view addSubview:editUserInfoBT];
    
    [editUserInfoBT addTarget:self action:@selector(editUserInfoAction:) forControlEvents:UIControlEventTouchUpInside];
    
    //灰色阴影
    UIView *view1 = [[UIView alloc]initWithFrame:CGRectMake(0, 150, self.view.frame.size.width, self.view.frame.size.height - 150)];
    if (iPhone5) {
        view1.frame = CGRectMake(0, 130, self.view.frame.size.width, self.view.frame.size.height - 150);
    }else if (iPhone4s){
        view1.frame = CGRectMake(0, 125, self.view.frame.size.width, self.view.frame.size.height - 150);
    }
    view1.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    [self.view addSubview:view1];
    
    self.settingTableView = [[UITableView alloc]initWithFrame:CGRectMake(0, 95, self.view.frame.size.width, view1.frame.size.height - 95) style:UITableViewStylePlain];
    if (iPhone4s) {
        self.settingTableView.frame = CGRectMake(0, 70, self.view.frame.size.width, view1.frame.size.height - 70);
    }
    self.settingTableView.backgroundColor = [UIColor clearColor];
    self.settingTableView.delegate = self;
    self.settingTableView.dataSource = self;
    self.settingTableView.bounces = NO;
//    取消cell的线
    self.settingTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [view1 addSubview:self.settingTableView];
    
//    设置头像区域
    self.imageView = [[UIImageView alloc]initWithFrame:CGRectMake(self.view.frame.size.width/2 - 50,view1.frame.origin.y - 50, 100, 100)];
    self.imageView.backgroundColor = [UIColor redColor];
    //设置圆角
    self.imageView.layer.masksToBounds = YES;
   self.imageView.layer.cornerRadius = 50;
    //边框宽度
    self.imageView.layer.borderWidth = 5;
    //边框颜色
    self.imageView.layer.borderColor = [UIColor whiteColor].CGColor;
    if (iPhone4s) {
        self.imageView.frame = CGRectMake(self.view.frame.size.width/2 - 50,view1.frame.origin.y - 50, 80, 80);
        self.imageView.layer.cornerRadius = 40;
    }

    [self.view addSubview:self.imageView];
    
//    打开UIImageView的用户交互性
    self.imageView.userInteractionEnabled = YES;
    
    UIGestureRecognizer *editGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(editUserInfoTap:)];
    [self.imageView addGestureRecognizer:editGR];
    
//    清除缓存按钮
    UIButton *cleanCacheBT = [UIButton buttonWithType:UIButtonTypeCustom];
    cleanCacheBT.frame = CGRectMake(WIDTH - 100, view1.frame.origin.y - 40, 80, 30);
    [cleanCacheBT setTitle:@"清清我吧" forState:UIControlStateNormal];
    [cleanCacheBT setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
    cleanCacheBT.layer.masksToBounds = YES;
    cleanCacheBT.layer.cornerRadius = 8.0f;
    [self.view addSubview:cleanCacheBT];
    
    [cleanCacheBT addTarget:self action:@selector(cleanCacheAction:) forControlEvents:UIControlEventTouchUpInside];
    
//设置导师姓名
    
    self.nameLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, view1.frame.origin.y + 50, WIDTH - 20, 30)];
    if (iPhone4s) {
        self.nameLabel.frame = CGRectMake(0, view1.frame.origin.y + 40, WIDTH - 20, 30);
    }
    self.nameLabel.textColor = [UIColor colorWithRed:71 / 255.0 green:56 / 255.0 blue:57 / 255.0 alpha:0.6];
    self.nameLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:self.nameLabel];
//    [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.imageView sd_setImageWithURL:[NSURL URLWithString:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"iconUrl"]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        self.nameLabel.text = [[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"nickName"];
    } else {
    
        self.nameLabel.text = @"未登录";
    }
}

#pragma mark -- tapGestureAction
-(void)editUserInfoTap:(UITapGestureRecognizer *)tap
{
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        EditUserInfoViewController *editUsersVC = [[EditUserInfoViewController alloc] init];
        editUsersVC.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
        
        [self presentViewController:editUsersVC animated:YES completion:^{
            
            
        }];
    } else {
        
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您还未登录，请先登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
        [self.view addSubview:alert];
        
        [alert show];
        
        alert.delegate = self;
    }
}

#pragma mark -- buttonAction
-(void)editUserInfoAction:(UIButton *)button
{
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        EditUserInfoViewController *editUsersVC = [[EditUserInfoViewController alloc] init];
        editUsersVC.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
        
        [self presentViewController:editUsersVC animated:YES completion:^{
            
            
        }];
    } else {
    
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您还未登录，请先登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
        [self.view addSubview:alert];
        
        [alert show];
        
        alert.delegate = self;
    }
}

-(void)cleanCacheAction:(UIButton *)button
{
    NSUInteger size = [[SDImageCache sharedImageCache] getSize];
    
    CGFloat mb = size / 1024.0 / 1024.0;
    NSString *imageSize = [NSString stringWithFormat:@"缓存数据:%.2fMB",mb];
    
    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:imageSize message:@"是否清除缓存数据" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    [alert show];
    
    //[self.settingTableView reloadData];
}

#pragma mark -- alertViewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"您还未登录，请先登录"] || [alertView.message isEqualToString:@"您还未登录哦，亲。。。"]) {
        
        if (buttonIndex == 1) {
            
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            loginVC.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
            
            [self presentViewController:loginVC animated:YES completion:^{
                
                
            }];
        }
    }
    
    if ([alertView.message isEqualToString:@"是否清除缓存数据"]) {
        
        [[SDImageCache sharedImageCache] cleanDisk];
        
        if (buttonIndex == 1) {
            
            dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
                
                NSString *cachPath = [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES) objectAtIndex:0];
                NSArray *files = [[NSFileManager defaultManager] subpathsAtPath:cachPath];
                
                for (NSString *p in files) {
                    
                    NSError *error;
                    NSString *path = [cachPath stringByAppendingPathComponent:p];
                    if ([[NSFileManager defaultManager] fileExistsAtPath:path]) {
                        
                        [[NSFileManager defaultManager] removeItemAtPath:path error:&error];
                    }
                }
                [self performSelectorOnMainThread:@selector(clearCacheSuccess) withObject:nil waitUntilDone:YES];
            });
        }
    }
}
//清除缓存成功
-(void)clearCacheSuccess
{
    [self showAlert:@"清除成功"];
}


#pragma mark -- tableviewDelegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{

    return 6;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (iPhone6P) {
        return 65;
    }else if (iPhone5){
        return 45;
    }else if (iPhone4s){
        return 35;
    }
    return 50;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *cellIdentifier = @"myCell";
    settingTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        cell = [[settingTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;//取消cell点击效果
        cell.backgroundColor = [UIColor clearColor];
    }
    
    
    NSArray *titles = @[@"  成为导师",@"  你的导师", @"  导师主页", @"  查看评论", @"  账户安全",@"  关于我们"];
    if (iPhone6P) {
        cell.textLabel.font = [UIFont systemFontOfSize:23];
    }else if (iPhone6){
        cell.textLabel.font = [UIFont systemFontOfSize:17];
    }else if (iPhone5){
        cell.textLabel.font = [UIFont systemFontOfSize:15];
    }else if (iPhone4s){
        cell.textLabel.font = [UIFont systemFontOfSize:12];
    }
    if (indexPath.row == 5) {
        
        UILabel *label = [[UILabel alloc]init];
        if (iPhone6) {
            label.frame = CGRectMake(25, 150 + 80+ cell.frame.size.height * 6 , self.view.frame.size.width - 50 ,0.5);
        }else if(iPhone6P){
            label.frame = CGRectMake(25, 150 + 160 + cell.frame.size.height * 6 , self.view.frame.size.width - 50  ,0.5);
        }else if (iPhone5){
            label.frame = CGRectMake(25, 150 + 35 + cell.frame.size.height * 6 , self.view.frame.size.width - 50  ,0.5);
        }else if (iPhone4s){
            label.frame = CGRectMake(25, 110 + cell.frame.size.height * 6 , self.view.frame.size.width - 50  ,0.5);
        }
        label.backgroundColor = [UIColor colorWithRed:71 / 255.0 green:56 / 255.0 blue:57 / 255.0 alpha:0.6];
        [self.view addSubview:label];
    }

    cell.textLabel.text = titles[indexPath.row];
    cell.textLabel.textColor = [UIColor colorWithRed:71 / 255.0 green:56 / 255.0 blue:57 / 255.0 alpha:0.6];
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 0) {
        
        applyTutorViewController *applyTutorVC = [[applyTutorViewController alloc]init];
        [self.navigationController pushViewController:applyTutorVC animated:YES];
    } else if (indexPath.row ==1) {
    
        if (![[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您还未登录哦，亲。。。" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录" ,nil];
            [self.view addSubview:alert];
            
            [alert show];
        } else {
            
            YourTutorsViewController *yourTutorVC = [[YourTutorsViewController alloc] init];
            [self.navigationController pushViewController:yourTutorVC animated:YES];
        }
    } else if (indexPath.row == 2){
        
        if (![[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您还未登录哦，亲。。。" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
            [self.view addSubview:alert];
            
            [alert show];
        } else {
          
            NSDictionary *dic = [[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"];

    for (NSString *key in dic.allKeys) {
                
        if ([key isEqualToString:@"teacherID"]) {
            
            [TagForClient shareTagDataHandle].isTeacher = YES;
            break;
        }
        else{
                
            [TagForClient shareTagDataHandle].isTeacher = NO;
                }
            }
            
            if ([TagForClient shareTagDataHandle].isTeacher) {
                
                EditTutorInfoViewController *editTutorVC = [[EditTutorInfoViewController alloc] init];
                NSString *tid = [dic objectForKey:@"teacherID"];
                editTutorVC.toDetialID = tid;

                [self.navigationController pushViewController:editTutorVC animated:YES];
            } else {
            
                UIAlertView *alertV = [[UIAlertView alloc]initWithTitle:@"提示" message:@"很遗憾，您不是导师，没有权限。" delegate:self cancelButtonTitle:@"确认" otherButtonTitles:nil, nil];
                [alertV show];
            }
        }
    } else if (indexPath.row == 5) {
        
        aboutUsViewController *aboutUsVC = [[aboutUsViewController alloc]init];
        [self.navigationController pushViewController:aboutUsVC animated:YES];
    } else if (indexPath.row == 4 ){
        
        if (![[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您还未登录哦，亲。。。" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
            [self.view addSubview:alert];
            
            [alert show];
        } else {
            AccountSecurityViewController *accountSecurityVC = [[AccountSecurityViewController alloc]init];
            [self.navigationController pushViewController:accountSecurityVC animated:YES];
        }
    } else {
        
        if (![[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您还未登录哦，亲。。。" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
            [self.view addSubview:alert];
            
            [alert show];
        } else {
            watchCommentViewController *accountSecurityVC = [[watchCommentViewController alloc]init];
            [self.navigationController pushViewController:accountSecurityVC animated:YES];
        }
    }
}

#pragma mark -- viewWillAppear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.tabBarController.tabBar.hidden = NO;
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.imageView sd_setImageWithURL:[NSURL URLWithString:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"iconUrl"]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        self.nameLabel.text = [[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"nickName"];
        
    } else {
    
        self.nameLabel.text = @"未登录";
        self.imageView.image = [UIImage imageNamed:@"placeholders.png"];
    }
}
-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.tabBarController.tabBar.hidden = NO;
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.imageView sd_setImageWithURL:[NSURL URLWithString:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"iconUrl"]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
        self.nameLabel.text = [[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"nickName"];

    } else {
    
        self.nameLabel.text = @"未登录";
        self.imageView.image = [UIImage imageNamed:@"placeholders.png"];
    }
}

#pragma mark -- 警示框回弹自动
- (void) showAlert:(NSString *)message{//时间
    
    UIAlertView *promptAlert = [[UIAlertView alloc] initWithTitle:@"提示" message:message delegate:nil cancelButtonTitle:nil otherButtonTitles:nil];
    
    [NSTimer scheduledTimerWithTimeInterval:1.0f
                                     target:self
                                   selector:@selector(timerFireMethod:)
                                   userInfo:promptAlert
                                    repeats:YES];
    [promptAlert show];
}

- (void)timerFireMethod:(NSTimer *)theTimer//弹出框
{
    UIAlertView *promptAlert = (UIAlertView * )[theTimer userInfo];
    [promptAlert dismissWithClickedButtonIndex:0 animated:NO];
    promptAlert = NULL;
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
