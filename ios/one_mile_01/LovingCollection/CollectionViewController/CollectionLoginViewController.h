//
//  CollectionLoginViewController.h
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/1.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "collectionDataBase.h"
#import "collectionModel.h"
#import "tutorHomeViewController.h"
#import "AFNConnect.h"
@interface CollectionLoginViewController : UIViewController

@property (nonatomic, strong) UITableView *collectionTV;
@property (nonatomic, strong) NSMutableArray *collectionLovingArray; //数据库
@property(nonatomic,assign)BOOL isUpLoading;
@property (assign,nonatomic)NSInteger nextPage;//上拉加载

@end
