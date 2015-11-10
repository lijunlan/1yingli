//
//  aboutUsViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/27.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "aboutUsViewController.h"

@interface aboutUsViewController ()

@end

@implementation aboutUsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    self.scrollV = [[UIScrollView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y + NAVIGATIONHEIGHT, self.view.frame.size.width, self.view.frame.size.height - NAVIGATIONHEIGHT)];
     self.scrollV.backgroundColor = [UIColor whiteColor];
    self.scrollV.showsVerticalScrollIndicator = NO;
     self.scrollV.bounces = NO;
    [self.view addSubview: self.scrollV];
    
    //返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(18, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(backbutton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    //关于我们
    self.navigationController.navigationBar.hidden = YES;
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(self.view.frame.size.width / 2 - 50, button.frame.origin.y,100, 30)];
    titleLabel.text = @"关于我们";
    titleLabel.font = [UIFont fontWithName:@"TimesNewRomanPS-BoldMT" size:20.0f];
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    self.contentLabel = [[UILabel alloc]initWithFrame:CGRectMake(3, 5, WIDTH - 6, HEIGHT - NAVIGATIONHEIGHT)];
    self.contentLabel.numberOfLines = 0;
    self.contentLabel.text = @"当你不知道留学怎样申请的时候，当你对自己的专业有疑惑的时候，当实习面临难以解决的困难时，你会怎么做？\n放弃？\n或者继续做自己不喜欢的工作？\n……\n你想找到为你解答难题的人吗？\n一英里为此而出现。\n一英里是致力于搭建便捷畅通的全方位大学生线上交流平台，连接高校名企精英与敢于逐梦的学子，破除沟通不便造成的信息壁垒，让信息获取兼具针对性与实惠性。\n我们做这个平台的初衷是因为现在的大学生在做选择的时候往往被社会的期待、父母的想法而左右，我们希望大学生能从事what you want to do而非what you are supposed to do。\n如果能将你们的经历经验分享给现在可能迷茫的大学生，就能让他们对自己未来的人生规划多一些认识，多一点把握。\n一英里，你离梦想只差一英里。";
    [self.scrollV addSubview:self.contentLabel];
    self.contentLabel.frame = CGRectMake(3, 5, WIDTH - 6,[aboutUsViewController heightForcell:self.contentLabel.text]);
    self.contentLabel.adjustsFontSizeToFitWidth = YES;
    [self resetContent];

    
    // Do any additional setup after loading the view.
}
//首行缩进  和 行高设定
- (void)resetContent{
    
    NSMutableAttributedString *attributedString = [[NSMutableAttributedString alloc] initWithString:self.contentLabel.text];
    NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
    paragraphStyle.alignment = NSTextAlignmentLeft;
    paragraphStyle.maximumLineHeight = 18;  //最大的行高
        paragraphStyle.lineSpacing = 8;  //行自定义行高度
    [paragraphStyle setFirstLineHeadIndent:37];//首行缩进
    [attributedString addAttribute:NSParagraphStyleAttributeName value:paragraphStyle range:NSMakeRange(0, [self.contentLabel.text length])];
    self.contentLabel.attributedText = attributedString;
    [self.contentLabel sizeToFit];

}
#pragma mark -- fitToHeightForCell
+(CGFloat)heightForcell:(NSString *)content
{
    //参数1.设置计算内容对的宽和高
    //参数2.设置计算高度模式
    //参数3.设置计算字体大小属性
    //参数4.系统备用参数,设置为nil
    
    //其中宽度一定与显示内容的label宽度一致,否则计算不准确
    CGSize size = CGSizeMake([UIScreen mainScreen].bounds.size.width - 6, 10000);
    
    //字体大小一定与显示内容的label字体大小一致
    NSDictionary *dic = [NSDictionary dictionaryWithObject:[UIFont systemFontOfSize:17] forKey:NSFontAttributeName];
    
    CGRect frame = [content boundingRectWithSize:size options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil];
    return frame.size.height;
}



-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.scrollV.contentSize = CGSizeMake(0, self.contentLabel.frame.origin.y + self.contentLabel.frame.size.height);
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.tabBarController.tabBar.hidden = YES;
}

-(void)backbutton:(UIButton *)button{
    [self.navigationController popViewControllerAnimated:YES];
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
