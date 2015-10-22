//
//  showMoreViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/6.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "showMoreViewController.h"

@interface showMoreViewController ()

@end

@implementation showMoreViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    
    UILabel *titleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 100, 40, 200, 30)];
    titleL.text = @"详情介绍";
    titleL.font = [UIFont systemFontOfSize:23.0f];
    titleL.textAlignment = NSTextAlignmentCenter;
    titleL.textColor = [UIColor blackColor];
    [self.view addSubview:titleL];
    
    //    返回按钮
    self.button = [UIButton buttonWithType:UIButtonTypeCustom];
    self.button.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [self.button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    self.button.backgroundColor = [UIColor clearColor];
    [self.button addTarget:self action:@selector(backButton:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:self.button];
    
    self.scrollV = [[UIScrollView alloc]initWithFrame:CGRectMake(self.view.frame.origin.x, self.button.frame.origin.y + self.button.frame.size.height + 5, self.view.frame.size.width, self.view.frame.size.height)];
    self.scrollV.showsVerticalScrollIndicator = NO;

    [self.view addSubview: self.scrollV];
    
    self.titleLabel.frame = CGRectMake(30, self.view.frame.origin.y + 10, 310, 70);
    self.titleLabel.textColor = [UIColor blackColor];
    self.titleLabel.numberOfLines = 2;
    self.titleLabel.textAlignment = NSTextAlignmentCenter;
    self.titleLabel.font = [UIFont systemFontOfSize:20 weight:10];
    self.titleLabel.backgroundColor = [UIColor whiteColor];
    [self.scrollV addSubview:self.titleLabel];
    
    self.contentLabel.frame = CGRectMake(self.button.frame.origin.x - 5, 0, self.view.frame.size.width - self.button.frame.origin.x * 2 + 10, 10000);
    self.contentLabel.backgroundColor = [UIColor whiteColor];
    self.contentLabel.numberOfLines = 0;
    self.contentLabel.font = [UIFont systemFontOfSize:17];
    [self.scrollV addSubview:self.contentLabel];
    
    NSString *path = @"/Users/wangjinshuai/one_mile_01/one_mile_01/one_mile_01/Resources";
    NSURL *baseURL = [NSURL fileURLWithPath:[path stringByAppendingPathComponent:@"personal.css"]];
    
    self.webV = [[UIWebView alloc]initWithFrame:CGRectMake(0, self.titleLabel.frame.origin.y + self.titleLabel.frame.size.height + 10, self.view.frame.size.width, self.view.frame.size.height - 80)];
    self.webV.backgroundColor = [UIColor whiteColor];
    self.webV.delegate = self;
    //取消webView的滑动条
    self.webV.dataDetectorTypes = UIDataDetectorTypeLink;
    
    for (UIView *_aView in [self.webV subviews])
    {
        if ([_aView isKindOfClass:[UIScrollView class]])
        {
            [(UIScrollView *)_aView setShowsVerticalScrollIndicator:NO];
            //右侧的滚动条
        }
    }
    
    [self.webV loadHTMLString:[NSString stringWithFormat:@"<div class=\"topic-content\">\n<p class=\"topic-content-p\" id=\"scontent\" style=\"text-align: justify;\">\n</p>\n</div> %@", self.contentLabel.text] baseURL:baseURL];
        [self.view addSubview: self.webV];
    // Do any additional setup after loading the view.
}

-(void)backButton:(UIButton *)button{

    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark -- fitToHeightForCell

+(CGFloat)heightForcell:(NSString *)content
{
    //参数1.设置计算内容对的宽和高
    //参数2.设置计算高度模式
    //参数3.设置计算字体大小属性
    //参数4.系统备用参数,设置为nil
    
    //其中宽度一定与显示内容的label宽度一致,否则计算不准确
    CGSize size = CGSizeMake([UIScreen mainScreen].bounds.size.width - 10 , 10000);
    
    //字体大小一定与显示内容的label字体大小一致
    NSDictionary *dic = [NSDictionary dictionaryWithObject:[UIFont systemFontOfSize:17.0] forKey:NSFontAttributeName];
    
    CGRect frame = [content boundingRectWithSize:size options:NSStringDrawingUsesLineFragmentOrigin attributes:dic context:nil];
    return frame.size.height;
}

-(void)viewWillAppear:(BOOL)animated{
    
      self.contentLabel.frame = CGRectMake(self.button.frame.origin.x - 5, self.titleLabel.frame.origin.y + self.titleLabel.frame.size.height - 10, self.view.frame.size.width - self.button.frame.origin.x * 2 + 10, [showMoreViewController heightForcell:self.contentLabel.text]+ 100);
    self.scrollV.contentSize = CGSizeMake(0, self.contentLabel.frame.origin.y + self.contentLabel.frame.size.height + 50);
    
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
