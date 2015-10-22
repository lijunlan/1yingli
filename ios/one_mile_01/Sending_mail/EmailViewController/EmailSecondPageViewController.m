//
//  EmailSecondPageViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/23.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EmailSecondPageViewController.h"

@interface EmailSecondPageViewController ()

@property (nonatomic, strong) UILabel *emailDetailL;
@property (nonatomic, strong) UILabel *dateDetailL;

@end

@implementation EmailSecondPageViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectMake(WIDTH / 2 - 70, 30, 140, NAVIGATIONHEIGHT - 30)];
    titleLabel.text = @"消息详情";
    titleLabel.font = [UIFont systemFontOfSize:35];
    titleLabel.font = [UIFont boldSystemFontOfSize:20.0f];
    titleLabel.textAlignment = NSTextAlignmentCenter;
    titleLabel.textColor = [UIColor lightGrayColor];
    [self.view addSubview:titleLabel];
    
    //    返回按钮
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.frame = CGRectMake(20, titleLabel.frame.origin.y + 10, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    [button setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    button.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:button];
    [button addTarget:self action:@selector(backButton:) forControlEvents:UIControlEventTouchUpInside];
    
    // Do any additional setup after loading the view.
    [self createSubviews];
}

-(void)createSubviews
{
    self.dateDetailL = [[UILabel alloc] initWithFrame:CGRectMake(10, NAVIGATIONHEIGHT + 30, WIDTH - 20, 20)];
    self.dateDetailL.font = [UIFont systemFontOfSize:14.0f];
    self.dateDetailL.textColor = [UIColor colorWithRed:158 / 255.0 green:159 / 255.0 blue:160 / 255.0 alpha:1.0];
    
    NSDate *tmpDate = [[NSDate alloc] initWithTimeIntervalSince1970:[self.emailDetailM.time doubleValue] / 1000.0];
    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateStyle:NSDateFormatterMediumStyle];
    [dateFormat setDateStyle:NSDateFormatterShortStyle];
    [dateFormat setDateFormat:@"yyyy-MM-dd\tHH:mm:ss"];
    
    self.dateDetailL.text = [dateFormat stringFromDate:tmpDate];
    [self.view addSubview:_dateDetailL];
    
    self.emailDetailL = [[UILabel alloc] initWithFrame:CGRectMake(_dateDetailL.frame.origin.x, _dateDetailL.frame.origin.y + _dateDetailL.frame.size.height + 10, _dateDetailL.frame.size.width, 100)];
    self.emailDetailL.numberOfLines = 0;
    //self.emailDetailL.backgroundColor = [UIColor yellowColor];
    self.emailDetailL.text = self.emailDetailM.title;
    self.emailDetailL.font = [UIFont systemFontOfSize:15.0f];
    self.emailDetailL.textColor = [UIColor colorWithRed:158 / 255.0 green:159 / 255.0 blue:160 / 255.0 alpha:1.0];
    
    [self.view addSubview:_emailDetailL];
}

-(void)backButton:(UIButton *)button{
    
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
