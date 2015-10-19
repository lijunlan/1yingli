//
//  submitViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/9/14.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "submitViewController.h"

@interface submitViewController ()

@end

@implementation submitViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    
    UIImageView *imageV = [[UIImageView alloc]init];
    [self.view addSubview:imageV];
    [imageV mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(180);
        make.left.equalTo(self.view.mas_left).offset(100);
        make.right.equalTo(self.view.mas_right).offset(-100);
        make.centerX.equalTo(self.view.mas_centerX);
    }];
    imageV.image = [UIImage imageNamed:@"applyForSubmit.png"];
    
    
    UILabel *label = [[UILabel alloc]init];
    [self.view addSubview:label];
    label.text = @"您的申请已经提交";
    label.font = [UIFont systemFontOfSize:20];
    [label mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(330);
        make.left.equalTo(self.view.mas_left).offset(100);
        make.right.equalTo(self.view.mas_right).offset(-100);
        make.centerX.equalTo(self.view.mas_centerX);
        
    }];
    
    
    UILabel *label2 = [[UILabel alloc]init];
    [self.view addSubview:label2];
    label2.text = @"我们会在24小时内联系您";
    label2.font = [UIFont systemFontOfSize:20];
    [label2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(360);
        make.left.equalTo(self.view.mas_left).offset(70);
        make.right.equalTo(self.view.mas_right).offset(-70);
        make.centerX.equalTo(self.view.mas_centerX);
        
    }];
    
    
    //    确定
    self.button = [[UIButton alloc]initWithFrame:CGRectMake(self.view.frame.origin.x,self.view.frame.size.height - 60,self.view.frame.size.width,60)];
    self.button.backgroundColor = [UIColor colorWithRed:71/255.0 green:172/255.0 blue:226/255.0 alpha:1.0];
    [self.button setTitle:@"确定" forState:UIControlStateNormal];
    [self.button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [self.button addTarget:self action:@selector(buttonAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:self.button];
    
}


-(void)buttonAction:(UIButton *)button{
    
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
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
