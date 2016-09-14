//
//  EmailLoginViewController.m
//  one_mile_01
//
//  Created by 王进帅 on 15/8/28.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "EmailLoginViewController.h"
#import "EmailFirstViewController.h"
#import "EmailViewController.h"

@interface EmailLoginViewController ()
@property (nonatomic,strong)EmailFirstViewController *emailFirstVC;
@property (nonatomic,strong)EmailViewController *emailVC;
@end

@implementation EmailLoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor yellowColor];
    self.emailFirstVC = [[EmailFirstViewController alloc]init];
    [self addChildViewController:self.emailFirstVC];
    [self.view addSubview:self.emailFirstVC.view];
    
    self.emailVC = [[EmailViewController alloc]init];
    [self addChildViewController:self.emailVC];
    [self.view addSubview:self.emailVC.view];
   
    
//    [[NSUserDefaults standardUserDefaults] setValue:@"0" forKey:@"isLogin"];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.view bringSubviewToFront:self.emailVC.view];
    } else {
        
        [self.view bringSubviewToFront:self.emailFirstVC.view];
    }
}


#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    //NSLog(@"123123");
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.view bringSubviewToFront:self.emailVC.view];
    } else {
        
        [self.view bringSubviewToFront:self.emailFirstVC.view];
    }
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.view bringSubviewToFront:self.emailVC.view];
    } else {
        
        [self.view bringSubviewToFront:self.emailFirstVC.view];
    }
}



    // Do any additional setup after loading the view.


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
