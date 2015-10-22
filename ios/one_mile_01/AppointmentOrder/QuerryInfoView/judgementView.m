//
//  judgementView.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "judgementView.h"

@interface judgementView ()<UIAlertViewDelegate>
{
    BOOL isCancel;
    NSInteger scoreForService;
    NSMutableArray *buttonArray;
}

@property (nonatomic, strong) UITextView *judgeTV;

@end

@implementation judgementView

-(instancetype)initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame]) {
        
        isCancel = NO;
        scoreForService = 1;
        buttonArray = [NSMutableArray array];
        
        [self createSubviews];
    }
    
    return self;
}

-(void)createSubviews
{
    UIView *judgeView = [[UIView alloc] initWithFrame:CGRectMake(30, HEIGHT / 2.0 - 150, WIDTH - 60, 240)];
    judgeView.backgroundColor = [UIColor whiteColor];
    judgeView.layer.masksToBounds = YES;
    judgeView.layer.cornerRadius = 15.0f;
    [self addSubview:judgeView];
    
    UILabel *judgeTitleL = [[UILabel alloc] initWithFrame:CGRectMake(judgeView.frame.size.width / 2.0 - 50, 5, 100, 30)];
    judgeTitleL.textAlignment = NSTextAlignmentCenter;
    judgeTitleL.font = [UIFont systemFontOfSize:15.0f];
    judgeTitleL.textColor = [UIColor colorWithRed:46 / 255.0 green:61 / 255.0 blue:77 / 255.0 alpha:1.0];
    judgeTitleL.text = @"学员评价";
    [judgeView addSubview:judgeTitleL];
    
    self.judgeTV = [[UITextView alloc] initWithFrame:CGRectMake(5, judgeTitleL.frame.origin.y + judgeTitleL.frame.size.height + 5, judgeView.frame.size.width - 10, judgeView.frame.size.height - judgeTitleL.frame.size.height - 5 * 6 - 30 * 2)];
    _judgeTV.layer.masksToBounds = YES;
    _judgeTV.layer.cornerRadius = 14.0f;
    _judgeTV.layer.borderWidth = 0.5f;
    _judgeTV.layer.borderColor = [[UIColor colorWithRed:214 / 255.0 green:214 / 255.0 blue:214 / 255.0 alpha:1.0] CGColor];
    [judgeView addSubview:_judgeTV];
    
    for (int i = 0; i < 5; i++) {
        
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        
        if (i < 2) {
            
            button.frame = CGRectMake(judgeView.frame.size.width / 2.0 - 30 * (2 - i) - 2 * (2 - i) - 15, _judgeTV.frame.origin.y + _judgeTV.frame.size.height + 5, 25, 25);
        } else if (i == 2) {
        
            button.frame = CGRectMake(judgeView.frame.size.width / 2.0 - 15, _judgeTV.frame.origin.y + _judgeTV.frame.size.height + 5, 25, 25);
        } else {
        
            button.frame = CGRectMake(judgeView.frame.size.width / 2.0 + 30 * (i - 3) + 2 * (i - 2) + 15, _judgeTV.frame.origin.y + _judgeTV.frame.size.height + 5, 25, 25);
        }
        button.tag = 10060 + i;
        [button setBackgroundImage:[UIImage imageNamed:@"comments_dislike.png"] forState:UIControlStateNormal];
        [judgeView addSubview:button];
        
        [button addTarget:self action:@selector(satisfyLevel:) forControlEvents:UIControlEventTouchUpInside];
        
        [buttonArray addObject:button];
    }
    
    UIButton *cancelForJudgeBT = [UIButton buttonWithType:UIButtonTypeCustom];
    cancelForJudgeBT.frame = CGRectMake(judgeView.frame.size.width / 2.0 - 90 - 10, _judgeTV.frame.origin.y + _judgeTV.frame.size.height + 30 + 10, 90, 25);
    cancelForJudgeBT.layer.masksToBounds = YES;
    cancelForJudgeBT.layer.cornerRadius = 5.0f;
    [cancelForJudgeBT setTitle:@"取消" forState:UIControlStateNormal];
    [cancelForJudgeBT setTitleColor:[UIColor colorWithRed:55 / 255.0 green:70 / 255.0 blue:85 / 255.0 alpha:1.0] forState:UIControlStateNormal];
    [cancelForJudgeBT setBackgroundColor:[UIColor colorWithRed:234 / 255.0 green:234 / 255.0 blue:234 / 255.0 alpha:1.0]];
    [judgeView addSubview:cancelForJudgeBT];
    
    [cancelForJudgeBT addTarget:self action:@selector(submitJudgeAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UIButton *querryForJudgeBT = [UIButton buttonWithType:UIButtonTypeCustom];
    querryForJudgeBT.frame = CGRectMake(cancelForJudgeBT.frame.origin.x + cancelForJudgeBT.frame.size.width + 20, cancelForJudgeBT.frame.origin.y, cancelForJudgeBT.frame.size.width, cancelForJudgeBT.frame.size.height);
    querryForJudgeBT.layer.masksToBounds = YES;
    querryForJudgeBT.layer.cornerRadius = 5.0f;
    [querryForJudgeBT setTitle:@"确定" forState:UIControlStateNormal];
    [querryForJudgeBT setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [querryForJudgeBT setBackgroundColor:[UIColor colorWithRed:72 / 255.0 green:173 / 255.0 blue:225 / 255.0 alpha:1.0]];
    [judgeView addSubview:querryForJudgeBT];
    
    [querryForJudgeBT addTarget:self action:@selector(submitJudgeAction:) forControlEvents:UIControlEventTouchUpInside];
    
    UIGestureRecognizer *resignGR = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(resignKeyboard:)];
    [self addGestureRecognizer:resignGR];
}

-(void)resignKeyboard:(UITapGestureRecognizer *)tap
{
    [self endEditing:YES];
}

-(void)satisfyLevel:(UIButton *)button
{
    scoreForService = 0;
    for (UIButton *tmpBT in buttonArray) {
        
        if (tmpBT.tag == button.tag) {
            
            break;
        }
        
        scoreForService ++;
    }
    
    for (int j = 0; j < buttonArray.count; j++) {
        
        UIButton *bt = [buttonArray objectAtIndex:j];
        
        if (j <= scoreForService) {
            
            [bt setBackgroundImage:[UIImage imageNamed:@"comments_favor.png"] forState:UIControlStateNormal];
        } else {
        
            [bt setBackgroundImage:[UIImage imageNamed:@"comments_dislike.png"] forState:UIControlStateNormal];
        }
    }
}

-(void)submitJudgeAction:(UIButton *)button
{
    if ([button.titleLabel.text isEqualToString:@"取消"]) {
        
        [UIView animateWithDuration:0.4f
                              delay:0.1f
                            options:UIViewAnimationOptionAllowAnimatedContent
                         animations:^{
            
                self.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
        } completion:^(BOOL finished) {
            
            
        }];
    } else {
    
        [UIView animateWithDuration:0.5f
                              delay:0.1f
                            options:UIViewAnimationOptionAllowAnimatedContent
                         animations:^{
                
                self.frame = CGRectMake(0, HEIGHT, WIDTH, HEIGHT);
                [self judgementForServiceData];
        } completion:^(BOOL finished) {
                                
                                
        }];
    }
}

#pragma mark -- 评价 数据请求
-(void)judgementForServiceData
{
    [AFNConnect AFNConnectWithUrl:HTTPREQUEST withBodyData:[AFNConnect createDataForJudgement:@"user" withMethod:@"commentTeacher" withOrderID:self.orderForJudgeM.orderId withTeacherID:self.orderForJudgeM.teacherId withScore:(scoreForService + 1) withContent:self.judgeTV.text withUserID:[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"uid"]] connectBlock:^(id data) {
        
        NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        if ([[dic objectForKey:@"state"] isEqualToString:@"success"]) {
            
            [self showAlert:@"评论成功"];
        } else {
            
            if ([[dic objectForKey:@"msg"] isEqualToString:@"order state is not accurate"]) {
                
                [self showAlert:@"订单已处理"];
            } else if ([[dic objectForKey:@"msg"] isEqualToString:@"uid is not existed"]) {
                
                [[NSUserDefaults standardUserDefaults] setObject:@"0" forKey:@"isLogin"];
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"登录超时，请重新登录" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"重新登录", nil];
                
                [alert show];
            }
        }
    }];
}

#pragma mark -- alertviewDelegate
-(void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    [self.myDelegate alertToLogin:buttonIndex];
}

//自动警示框回弹
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

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
