//
//  MainTabViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/31.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "MainTabViewController.h"
#import "RootViewController.h"
#import "CollectionViewController.h"
#import "EmailLoginViewController.h"
#import "OrderViewController.h"
#import "SettingViewController.h"
#import "MainTabBar.h"
#import "UIImage+UIImageByScaleToSize.h"

@interface MainTabViewController ()

@end

@implementation MainTabViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //home
    //@"home"
    RootViewController *rootVC = [[RootViewController alloc] init];
    [self addChildViewController:rootVC title:@"首页" image:@"tabbar_home_unselected@2x.png" selectedImage:@"tabbar_home_selected@2x.png"];
    
    //@"collection"
    CollectionViewController *collectionVC = [[CollectionViewController alloc] init];
    [self addChildViewController:collectionVC title:@"心愿单" image:@"tabbar_favorite_unselected@2x.png" selectedImage:@"tabbar_favorite_selected@2x.png"];
    
    //@"email"
    EmailLoginViewController *emailVC = [[EmailLoginViewController alloc] init];
    [self addChildViewController:emailVC title:@"消息" image:@"tabbar_message_unselected@2x.png" selectedImage:@"tabbar_message_selected@2x.png"];
    
    //@"order"
    OrderViewController *orderVC = [[OrderViewController alloc] init];
    [self addChildViewController:orderVC title:@"订单" image:@"tabbar_order_unselected@2x.png" selectedImage:@"tabbar_order_selected@2x.png"];
    
    //@"setting"
    SettingViewController *settingVC = [[SettingViewController alloc] init];
    [self addChildViewController:settingVC title:@"我" image:@"tabbar_me_unselected@2x.png" selectedImage:@"tabbar_me_selected@2x.png"];

    MainTabBar *tabBar = [[MainTabBar alloc] init];
    
    [self setValue:tabBar forKey:@"tabBar"];
}

-(void)addChildViewController:(UIViewController *)childController title:(NSString *)title image:(NSString *)image selectedImage:(NSString *)selectedImage
{
    childController.title = title;
    
    childController.tabBarItem.image = [[UIImage imageNamed:image] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    childController.tabBarItem.selectedImage = [[UIImage imageNamed:selectedImage] imageWithRenderingMode:UIImageRenderingModeAlwaysOriginal];
    
    NSMutableDictionary *textAttr = [NSMutableDictionary dictionary];
    textAttr[NSForegroundColorAttributeName] = [UIColor blackColor];
    
    NSMutableDictionary *selectTextAttr = [NSMutableDictionary dictionary];
    selectTextAttr[NSForegroundColorAttributeName] = [UIColor colorWithRed:0 / 255.0 green:160 / 255.0 blue:255 / 255.0 alpha:1.0];
    
    [childController.tabBarItem setTitleTextAttributes:textAttr forState:UIControlStateNormal];
    [childController.tabBarItem setTitleTextAttributes:selectTextAttr forState:UIControlStateSelected];
    
    UINavigationController *navC = [[UINavigationController alloc] initWithRootViewController:childController];
    
    [self addChildViewController:navC];
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
