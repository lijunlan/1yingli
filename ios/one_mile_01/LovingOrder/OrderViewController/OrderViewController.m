//
//  OrderViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "OrderViewController.h"

@interface OrderViewController ()

@end

@implementation OrderViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor yellowColor];
    self.QuerryInfoVC = [[QuerryInfoViewController alloc]init];
    [self addChildViewController:self.QuerryInfoVC];
    [self.view addSubview:self.QuerryInfoVC.view];
    
    self.orderUnloginVC = [[OrderUnloginViewController alloc]init];
    [self addChildViewController:self.orderUnloginVC];
    [self.view addSubview:self.orderUnloginVC.view];

    self.tutorQuerryInfoVC = [[tutorQueeryInfoViewController alloc]init];
    [self addChildViewController:self.tutorQuerryInfoVC];
    [self.view addSubview:self.tutorQuerryInfoVC.view];
    
    
//    [[NSUserDefaults standardUserDefaults] setValue:@"0" forKey:@"isLogin"];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        if (((NSString *)[[[NSUserDefaults standardUserDefaults] objectForKey:@"userInfo"] objectForKey:@"teacherID"]).length != 0) {
        
            [TagForClient shareTagDataHandle].isTeacher = YES;
            [self.view bringSubviewToFront:self.tutorQuerryInfoVC.view];
            
        }else{

            [TagForClient shareTagDataHandle].isTeacher = NO;
            [self.view bringSubviewToFront:self.QuerryInfoVC.view];
        }
        
    } else {
        
        [self.view bringSubviewToFront:self.orderUnloginVC.view];
    }

}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        if ([TagForClient shareTagDataHandle].isTeacher) {
            
            [self.view bringSubviewToFront:self.tutorQuerryInfoVC.view];
            
        }else{
            
            [self.view bringSubviewToFront:self.QuerryInfoVC.view];
            
        }
    
    } else {
        
        [self.view bringSubviewToFront:self.orderUnloginVC.view];
    }
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        if ([TagForClient shareTagDataHandle].isTeacher) {
            
            [self.view bringSubviewToFront:self.tutorQuerryInfoVC.view];
            
        }else{
            
            [self.view bringSubviewToFront:self.QuerryInfoVC.view];
            
        }
    
    } else {
        
        [self.view bringSubviewToFront:self.orderUnloginVC.view];
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
