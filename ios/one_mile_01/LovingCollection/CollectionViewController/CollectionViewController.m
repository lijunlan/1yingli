//
//  CollectionViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "CollectionViewController.h"
#import "CollectionUnloginViewController.h"
#import "CollectionLoginViewController.h"
#import "JSONKit.h"

@interface CollectionViewController ()

@property (nonatomic, strong) CollectionUnloginViewController *unloginCVC;
@property (nonatomic, strong) CollectionLoginViewController *loginCVC;

@end

@implementation CollectionViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor yellowColor];
    self.navigationController.navigationBar.hidden = YES;
    
    UIImageView *imageV = [[UIImageView alloc]initWithFrame:[[UIScreen mainScreen]bounds]];
    imageV.image = [UIImage imageNamed:@"collection_bg.png"];
    [self.view addSubview:imageV];

    self.unloginCVC = [[CollectionUnloginViewController alloc] init];
    [self addChildViewController:_unloginCVC];
    [self.view addSubview:_unloginCVC.view];
    
    self.loginCVC = [[CollectionLoginViewController alloc] init];
    [self addChildViewController:_loginCVC];
    [self.view addSubview:_loginCVC.view];
    
    //[[NSUserDefaults standardUserDefaults] setValue:@"0" forKey:@"isLogin"];
    
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.view bringSubviewToFront:self.loginCVC.view];
    } else {
    
        [self.view bringSubviewToFront:self.unloginCVC.view];
    }
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    //NSLog(@"123123");
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.view bringSubviewToFront:self.loginCVC.view];
    } else {
        
        [self.view bringSubviewToFront:self.unloginCVC.view];
    }
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
        
    if ([[[NSUserDefaults standardUserDefaults] objectForKey:@"isLogin"] isEqualToString:@"1"]) {
        
        [self.view bringSubviewToFront:self.loginCVC.view];
    } else {
        
        [self.view bringSubviewToFront:self.unloginCVC.view];
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
