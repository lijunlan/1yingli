//
//  applyTutorViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/24.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "applyTutorViewController.h"
#import "WantTobeTutorViewController.h"

@interface applyTutorViewController ()<UIAlertViewDelegate>
@end

@implementation applyTutorViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor whiteColor];
    
    [self createSubviews];
    // Do any additional setup after loading the view.
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.tabBarController.tabBar.hidden = YES;
    
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.tabBarController.tabBar.hidden = YES;
}

-(void)createSubviews
{
    self.scrollView = [[UIScrollView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y, self.view.frame.size.width, self.view.frame.size.height + 100)];
    self.scrollView.bounces = NO;
    self.scrollView.pagingEnabled = NO;
    _scrollView.showsVerticalScrollIndicator = NO;
    self.scrollView.delegate = self;
    [self.view addSubview:self.scrollView];
    
    UILabel *titleLabel = [[UILabel alloc]init];
    [self.scrollView addSubview:titleLabel];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.scrollView.mas_top).offset(20);
        make.bottom.equalTo(self.scrollView.mas_bottom).offset(-(self.view.frame.size.height - 20));
        make.centerX.equalTo(self.scrollView.mas_centerX);
    }];
    titleLabel.text = @"成为导师";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textColor = [UIColor lightGrayColor];

    
    //    申请详情题目
    UILabel *titleLabel2 = [[UILabel alloc]init];
    [self.scrollView addSubview:titleLabel2];
    titleLabel2.text = @"申请详情";
    [titleLabel2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.scrollView.mas_top).offset(self.view.frame.size.height + 30);
        make.left.equalTo(self.scrollView.mas_left).offset(self.view.frame.size.width / 2 - 50);
        make.right.equalTo(self.scrollView.mas_right).offset(-(self.view.frame.size.width / 2 - 50));
        make.centerX.equalTo(self.scrollView.mas_centerX);
    }];
    
    titleLabel2.font = [UIFont systemFontOfSize:23];
    titleLabel2.textColor = [UIColor colorWithRed:129/255.0 green:130/255.0 blue:131/255.0 alpha:1.0];
    
    //    详细内容面黑体字
    UILabel *titleLabel3 = [[UILabel alloc]init];
    [self.scrollView addSubview:titleLabel3];
    [titleLabel3 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.scrollView.mas_top).offset(self.view.frame.size.height + 70);
        make.left.equalTo(self.scrollView.mas_left).offset( 15 );
        make.right.equalTo(self.scrollView.mas_right).offset(-15);
        make.centerX.equalTo(self.scrollView.mas_centerX);
    }];
    titleLabel3.text = @"如果您在某个领域有多年经验和独到见解，欢迎申请成为「一英里」的导师。";
    titleLabel3.textColor = [UIColor blackColor];
    titleLabel3.numberOfLines = 2;
    if (iPhone5) {
        titleLabel3.font = [UIFont systemFontOfSize:15 weight:10];
    }else{
        titleLabel3.font = [UIFont systemFontOfSize:20 weight:10];
    }
    

    //    详细介绍
    self.myLabel = [[UILabel alloc] init];
    self.myLabel.textColor = [UIColor colorWithRed:129/255.0 green:130/255.0 blue:131/255.0 alpha:1.0];
    self.myLabel.alpha = 1.0;
    self.myLabel.numberOfLines = 0;
    self.myLabel.text = @"［一英里」是一个O2O经验咨询平台，基于解决“个性化问题”而生。所谓个性化问题，是指“我想在北京开一个杯子蛋糕店，应该如何着手？”“我应该如何获得升职机会？”这样现实而复杂的、答案因人而异的问题。面对这种情况，通常人们会辗转托朋友去找到一个懂行的人，“吃个饭，聊一聊”。但每个人的社交圈有限，通过这种方式找到行家的不确定性很大，见面效率也很低。\n\n「一英里」希望通过共享经济的方式，让行家头脑中的信息发挥更大价值。当用户遇到“个性化问题”时，可以在这里找到一个有经验的行家，通过一对一的见面交谈，让行家帮忙答疑解惑、出谋划策。\n\n「一英里」是果壳网旗下产品，于2015年3月正式上线运营。\n\n成为「一英里」的导师，您可以：\n分享自己的经验和知识，帮助那些积极求教、热爱学习的人，享受解决问题的快感。\n结交到有意思的朋友，甚至是合作伙伴。参加仅限行家的内部活动，结识各个领域人脉；\n仅需聊聊天，将知识和经验转换成真金白银（「一英里」不抽取佣金 ）。您也可以通过我们，将交谈收入定期捐助给公益机构；\n通过学员的线上评价、分享和线下的口口相传，提升个人品牌。有初步表现之后，「一英里」团队会为您撰写个人故事，在更多媒体上传播您的个人品牌。";
    
    self.myLabel.textColor = [UIColor colorWithRed:129/255.0 green:130/255.0 blue:131/255.0 alpha:1.0];
    self.myLabel.adjustsFontSizeToFitWidth = YES;
    [self resetContent];

    if (iPhone5) {
        self.myLabel.font = [UIFont systemFontOfSize:11];
    }else if(iPhone6){
    self.myLabel.font = [UIFont systemFontOfSize:15];
    }else if(iPhone6P){
        self.myLabel.font = [UIFont systemFontOfSize:17];
    }
    [self.scrollView addSubview:self.myLabel];
    [self.myLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        
        make.top.equalTo(self.scrollView.mas_top).offset(self.view.frame.size.height + 100);
        make.left.equalTo(self.scrollView.mas_left).offset( 10 );
        make.right.equalTo(self.scrollView.mas_right).offset(-10);
        make.height.offset([applyTutorViewController heightForcell:self.myLabel.text] + 100);
    }];

    
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.scrollView addSubview:button];
    [button mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.scrollView.mas_top).offset(18);
        make.left.equalTo(self.scrollView.mas_left).offset(20);
        make.height.offset(PUSHANDPOPBUTTONSIZE);
        make.width.offset(PUSHANDPOPBUTTONSIZE);
    }];
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor whiteColor];
    [button addTarget:self action:@selector(backButton:) forControlEvents:UIControlEventTouchUpInside];
    
    
    //    一英里图片
    UIImageView *imageView1 = [[UIImageView alloc]init];
    [self.scrollView addSubview:imageView1];
    [imageView1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.scrollView.mas_top).offset(90);
        make.height.offset(35);
        make.left.equalTo(self.scrollView.mas_left).offset(WIDTH / 2 - 55);
        make.right.equalTo(self.scrollView.mas_right).offset(-(WIDTH / 2 - 55));
        make.centerX.equalTo(self.scrollView.mas_centerX);
    }];
    imageView1.image = [UIImage imageNamed:@"apply_onemile.png"];

    
    //   一英里下面的图片
    UIImageView *imageView2 = [[UIImageView alloc]init];
    [self.scrollView addSubview:imageView2];
    [imageView2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.scrollView.mas_top).offset(170);
        make.height.offset(self.view.frame.size.height - 180 - 200);
        make.width.offset(self.view.frame.size.width);
    
    }];
    imageView2.image = [UIImage imageNamed:@"apply_pic.png"];
    
    
    //    申请当导师按钮
    UIButton *button1 = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.scrollView addSubview:button1];
    [button1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.scrollView.mas_bottom).offset( -150);
        make.left.equalTo(self.scrollView.mas_left).offset(50);
        make.right.equalTo(self.scrollView.mas_right).offset(-50);
        make.height.offset(40);
        make.centerX.equalTo(self.scrollView.mas_centerX);
    }];
        button1.backgroundColor =[UIColor colorWithRed:72/255.0 green:173/255.0 blue:225/255.0 alpha:1.0];
    NSString *button1Sring = @"申请当导师";
    [button1 setTitle:button1Sring forState:UIControlStateNormal];
    button1.layer.masksToBounds = YES;
    button1.layer.cornerRadius = 10;
    [button1 addTarget:self action:@selector(applyButton:) forControlEvents:UIControlEventTouchUpInside];
    
    
    //    下拉按钮
    UIButton *button2 = [UIButton buttonWithType:UIButtonTypeCustom];
        [self.scrollView addSubview:button2];
    [button2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(self.scrollView.mas_bottom).offset( -70);
        make.left.equalTo(self.scrollView.mas_left).offset(self.view.frame.size.width / 2 - 45);
        make.right.equalTo(self.scrollView.mas_right).offset(-(self.view.frame.size.width / 2 - 45));
        make.height.offset(40);
        make.centerX.equalTo(self.scrollView.mas_centerX);
    }];
    
    [button2 setBackgroundImage:[UIImage imageNamed:@"apply_btnToIntro.png"] forState:UIControlStateNormal];
    [button2 addTarget: self action:@selector(drawButton:) forControlEvents:UIControlEventTouchUpInside];
}

//首行缩进  和 行高设定
- (void)resetContent{
    NSMutableAttributedString *attributedString = [[NSMutableAttributedString alloc] initWithString:self.myLabel.text];
    NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
    paragraphStyle.alignment = NSTextAlignmentLeft;
    paragraphStyle.maximumLineHeight = 10;  //最大的行高
    if (iPhone5) {
    paragraphStyle.lineSpacing = 5;  //行自定义行高度
    [paragraphStyle setFirstLineHeadIndent:self.myLabel.frame.size.width + 23];//首行缩进
    }else if (iPhone6){
    paragraphStyle.lineSpacing = 8;  //行自定义行高度
    [paragraphStyle setFirstLineHeadIndent:self.myLabel.frame.size.width + 30];//首行缩进
    }else if (iPhone6P){
    paragraphStyle.lineSpacing = 10;  //行自定义行高度
    [paragraphStyle setFirstLineHeadIndent:self.myLabel.frame.size.width + 35];//首行缩进

    }
    [attributedString addAttribute:NSParagraphStyleAttributeName value:paragraphStyle range:NSMakeRange(0, [self . myLabel . text length])];
        self.myLabel.attributedText = attributedString;
    [self.myLabel sizeToFit];
}


#pragma mark -- fitToHeightForCell
+(CGFloat)heightForcell:(NSString *)content
{
    if (iPhone5) {
        //其中宽度一定与显示内容的label宽度一致,否则计算不准确
        CGSize size = CGSizeMake([UIScreen mainScreen].bounds.size.width - 10 , 10000);
        //字体大小一定与显示内容的label字体大小一致
        NSDictionary *dic = [NSDictionary dictionaryWithObject:[UIFont systemFontOfSize:11] forKey:NSFontAttributeName];
        CGRect frame = [content boundingRectWithSize:size options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil];
        return frame.size.height;
    }else if(iPhone6P){
    CGSize size = CGSizeMake([UIScreen mainScreen].bounds.size.width - 10 , 10000);
    
    NSDictionary *dic = [NSDictionary dictionaryWithObject:[UIFont systemFontOfSize:17.0] forKey:NSFontAttributeName];
        CGRect frame = [content boundingRectWithSize:size options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil];
    return frame.size.height;
    }else {
        CGSize size = CGSizeMake([UIScreen mainScreen].bounds.size.width - 10 , 10000);

        NSDictionary *dic = [NSDictionary dictionaryWithObject:[UIFont systemFontOfSize:15.0] forKey:NSFontAttributeName];
        CGRect frame = [content boundingRectWithSize:size options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil];
        return frame.size.height;
    }
}

-(void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    if (scrollView.contentOffset.y <= [UIScreen mainScreen].bounds.size.height) {
        self.scrollView.contentSize = CGSizeMake(0,[UIScreen mainScreen].bounds.size.height + 20);

    }
}

//下拉按钮
-(void)drawButton:(UIButton *)button{
    
   [self.scrollView setContentOffset:CGPointMake(0, [[UIScreen mainScreen] bounds].size.height) animated:YES];
   self.scrollView.contentSize = CGSizeMake(0,[UIScreen mainScreen].bounds.size.height * 2 + 64);
}


//返回按钮
-(void)backButton:(UIButton *)button{
    [self.navigationController popViewControllerAnimated:YES];
}


-(void)applyButton:(UIButton *)button
{
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        if (((NSString *)[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"]).length == 0) {
            
            WantTobeTutorViewController *toBeVC = [[WantTobeTutorViewController alloc] init];
            
            toBeVC.modalPresentationStyle = UIModalTransitionStyleFlipHorizontal;
            
            [self.navigationController presentViewController:toBeVC animated:YES completion:^{
                
                
            }];
        } else {
        
            UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:@"提示" message:@"您已经是导师了哦" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
            [alertV show];
        }
    } else {
        
        UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:@"提示" message:@"还没有登录哦。。。" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"登录", nil];
        [alertV show];
    }
}

-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ([alertView.message isEqualToString:@"还没有登录哦。。。"]) {
        
        if (buttonIndex == 1) {
            
            LoginViewController *loginVC = [[LoginViewController alloc] init];
            
            loginVC.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
            
            [self presentViewController:loginVC animated:YES completion:^{
                
            }];
        }
    }
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
