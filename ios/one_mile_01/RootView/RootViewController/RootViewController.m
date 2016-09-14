//
//  RootViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/8/21.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "RootViewController.h"
#import "RootTableViewCell.h"
#import "PilotTableViewCell.h"
//push到留学领航
#import "StudyPilotViewController.h"
#import "StudyPilotModel.h"
#import "tutorHomeViewController.h"
#import "ApplyjobsViewController.h"
#import "EntrepreneurialViewController.h"
#import "CampusViewController.h"
#import "ShareViewController.h"

//搜索
#import "SearchTutorViewController.h"

#import "UIImage+UIImageByScaleToSize.h"

#define CELLHEIGHTFOR6 250
#define CELLHEIGHTFOR6P 276
#define CELLHEIGHTFOR5 213.1

@interface RootViewController ()

@property (nonatomic, strong) UIView *searchView;
@property (nonatomic, strong) UIButton *searchBT;
@property (nonatomic, strong) NSMutableArray *studyPilotForHomeArray;
@property (nonatomic, strong) NSMutableArray *resultDataArray;
@property (nonatomic, assign) BOOL isSearch;

@end

@implementation RootViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.studyPilotForHomeArray = [NSMutableArray array];
        self.isSearch = NO;
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor whiteColor];
    
    
    self.homeTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, WIDTH, HEIGHT) style:UITableViewStylePlain];
    self.homeTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    _homeTableView.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    [self.view addSubview:_homeTableView];
    
    self.homeTableView.dataSource = self;
    self.homeTableView.delegate = self;
    
    self.homeTableView.bounces = NO;
    self.homeTableView.showsVerticalScrollIndicator = NO;
    
    //_homeTableView.separatorInset = UIEdgeInsetsZero;
    
    self.searchView = [[UIView alloc] initWithFrame:CGRectMake(33, 35, 40, 40)];
    _searchView.backgroundColor = [UIColor whiteColor];
    _searchView.layer.masksToBounds = YES;
    _searchView.layer.cornerRadius = 20.0;
    _searchView.layer.borderWidth = 2;
    _searchView.layer.borderColor = [[UIColor colorWithRed:140 / 255.0 green:142 / 255.0 blue:143 / 255.0 alpha:0.8] CGColor];
    [self.view addSubview:_searchView];
    
    self.searchBT = [UIButton buttonWithType:UIButtonTypeCustom];
    [_searchBT setBackgroundImage:[UIImage imageNamed:@"search.png"] forState:UIControlStateNormal];
    _searchBT.frame = CGRectMake(9, 9, self.searchView.frame.size.width - 18, self.searchView.frame.size.height - 18);
    [self.searchView addSubview:_searchBT];
    
    [_searchBT addTarget:self action:@selector(startSearchAction:) forControlEvents:UIControlEventTouchUpInside];
    
    self.searchTF = [[UITextField alloc] init];
//    _searchTF.placeholder = @"查找导师";
    self.searchTF.font = [UIFont systemFontOfSize:15.0f];
    _searchTF.returnKeyType = UIReturnKeyDone;
    _searchTF.clearButtonMode = UITextFieldViewModeAlways;
//
    _searchTF.delegate = self;
//
    [_searchView addSubview:_searchTF];
    
//    [self getListData];
}

-(void)startSearchAction:(UIButton *)button
{
    _isSearch = !_isSearch;
    if (_isSearch) {
        
        [UIView animateWithDuration:1.0 animations:^{
            
            self.searchView.frame = CGRectMake(WIDTH / 2.0 - 140, _searchView.frame.origin.y, 280, 40);
            self.searchView.layer.cornerRadius = 5.0f;
            self.searchTF.frame = CGRectMake(15 + 30, 5, _searchView.frame.size.width - 6 - 9 - 30, _searchView.frame.size.height - 10);
            
            self.searchTF.placeholder = @"查找导师";
        }];
    } else {
        
        [self.searchTF resignFirstResponder];
        [UIView animateWithDuration:1.0 animations:^{
            
            self.searchView.frame = CGRectMake(33, 35, 40, 40);
            self.searchView.layer.cornerRadius = 20.0f;
            self.searchTF.frame = CGRectZero;
        }];
    }
}

#pragma mark -- textfieldDelegate
-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    if (textField.text.length == 0) {
        
        
    } else {
    
        SearchTutorViewController *searchVC = [[SearchTutorViewController alloc] init];
        searchVC.searchKey = textField.text;
        searchVC.stateForSearch = 1;
        [self.navigationController pushViewController:searchVC animated:YES];
    }
    [textField resignFirstResponder];
    return NO;
}

-(void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    [self.searchTF resignFirstResponder];
}

-(void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
//    if (decelerate) {
//        
        [UIView animateWithDuration:1.0 animations:^{
            
            self.searchView.frame = CGRectMake(33, 35, 40, 40);
            self.searchView.layer.cornerRadius = 20.0f;
            self.searchTF.frame = CGRectZero;
        }];
//    }
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = NO;
}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = NO;
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 5 + _studyPilotForHomeArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (iPhone5 || iPhone4s) {
        
        if (indexPath.row < 5 || _studyPilotForHomeArray.count == 0) {
            
            return CELLHEIGHTFOR5;
        } else {
        
            return COMMONCELLHEIGHT;
        }
    } else if (iPhone6P) {
        
        if (indexPath.row < 5 || _studyPilotForHomeArray.count == 0) {
            
            return CELLHEIGHTFOR6P;
        } else {
        
            return COMMONCELLHEIGHT;
        }
    }
    
    if (indexPath.row < 5 || _studyPilotForHomeArray.count == 0) {
        
        return CELLHEIGHTFOR6;
    } else {
        
        return COMMONCELLHEIGHT;
    }
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *homeCellIdentifier = @"cellForHome";
    static NSString *commonRootCellIdentifier = @"cellForCommon";
    
    RootTableViewCell *rootCell = [tableView dequeueReusableCellWithIdentifier:homeCellIdentifier];
    
    PilotTableViewCell *commonCell = [tableView dequeueReusableCellWithIdentifier:commonRootCellIdentifier];
    
    if (indexPath.row < 5) {
        
        if (rootCell == nil) {
            
            rootCell = [[RootTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:homeCellIdentifier];
            rootCell.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
            rootCell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        
    } else {
        
        if (commonCell == nil) {
            
            commonCell = [[PilotTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:commonRootCellIdentifier];
            commonCell.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
            commonCell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
    }
    
    if (indexPath.row < 5) {
        
        CGSize imageSize;
        if (iPhone5 || iPhone4s) {
            imageSize = CGSizeMake(WIDTH, CELLHEIGHTFOR5);
        } else if (iPhone6) {
            imageSize = CGSizeMake(WIDTH, CELLHEIGHTFOR6);
        } else if (iPhone6P) {
            imageSize = CGSizeMake(WIDTH, CELLHEIGHTFOR6P);
        }
        if (indexPath.row == 0) {
            
            UIImage *image = [UIImage imageNamed:@"root_01.png"];
            UIColor *bgColor = [UIColor colorWithPatternImage:[image imageByScalingToSize:imageSize]];
            rootCell.backgroundColor = bgColor;
            rootCell.mainLabel.text = @"留学申请";
            rootCell.subLabel.text = @"开阔你的眼界和经验";
        } else if (indexPath.row == 1) {
            
            UIImage *image = [UIImage imageNamed:@"root_04.png"];
            UIColor *bgColor = [UIColor colorWithPatternImage:[image imageByScalingToSize:imageSize]];
            rootCell.backgroundColor = bgColor;
            rootCell.mainLabel.text = @"求职就业";
            rootCell.subLabel.text = @"为你开启企业招人秘籍";
        } else if (indexPath.row == 2) {
            
            UIImage *image = [UIImage imageNamed:@"root_02.png"];
            UIColor *bgColor = [UIColor colorWithPatternImage:[image imageByScalingToSize:imageSize]];
            rootCell.backgroundColor = bgColor;
            rootCell.mainLabel.text = @"创业助力";
            rootCell.subLabel.text = @"给你最有力的支持";
        } else if (indexPath.row == 3) {
            
            UIColor *bgColor = [UIColor colorWithPatternImage:[[UIImage imageNamed:@"root_03.png"] imageByScalingToSize:imageSize]];
            rootCell.backgroundColor = bgColor;
            rootCell.mainLabel.text = @"校园生活";
            rootCell.subLabel.text = @"有趣的校园经历";
        } else {
            
            UIColor *bgColor = [UIColor colorWithPatternImage:[[UIImage imageNamed:@"root_05.png"] imageByScalingToSize:imageSize]];
            rootCell.backgroundColor = bgColor;
            rootCell.mainLabel.text = @"猎奇分享";
            rootCell.subLabel.text = @"分享快乐有趣的故事";
        }
        
        return rootCell;
    } else {
        
        if (_studyPilotForHomeArray.count == 0) {
            
            return commonCell;
        }
        
        StudyPilotModel *tmp = [_studyPilotForHomeArray objectAtIndex:indexPath.row - 5];
        [commonCell.tutorIV sd_setImageWithURL:[NSURL URLWithString:[tmp.iconUrl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]]];
        commonCell.tutorNameL.text = tmp.name;
        commonCell.topicL.text = tmp.serviceTitle;
        commonCell.hasSeenL.text = [NSString stringWithFormat:@"%@ 人见过", tmp.likeNo];
        commonCell.clickNumberL.text = [NSString stringWithFormat:@"%@/ 次", tmp.level];
        commonCell.positionL.text = tmp.position;
        
        return commonCell;
    }
}

#pragma mark -- cellSelect
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self.searchTF resignFirstResponder];
    
    if (indexPath.row == 0) {
        
        StudyPilotViewController *studyPilotVC = [[StudyPilotViewController alloc] init];
            
        [self.navigationController pushViewController:studyPilotVC animated:YES];
            
    } else if (indexPath.row == 1) {
            
        ApplyjobsViewController *applyJobsVC = [[ApplyjobsViewController alloc] init];
            
        [self.navigationController pushViewController:applyJobsVC animated:YES];
    } else if (indexPath.row == 2) {
        
        EntrepreneurialViewController *entreVC = [[EntrepreneurialViewController alloc] init];
        
        [self.navigationController pushViewController:entreVC animated:YES];
    } else if (indexPath.row == 3) {
        
        CampusViewController *campusVC = [[CampusViewController alloc] init];
        
        [self.navigationController pushViewController:campusVC animated:YES];
    } else if (indexPath.row == 4) {
        
        ShareViewController *shareVC = [[ShareViewController alloc] init];
        
        [self.navigationController pushViewController:shareVC animated:YES];
    }
    if (_studyPilotForHomeArray.count != 0) {
        
        if (indexPath.row > 4) {
            
            tutorHomeViewController *tutorHomeVC = [[tutorHomeViewController alloc] init];
            
            StudyPilotModel *tmp = [_studyPilotForHomeArray objectAtIndex:indexPath.row - 5];
            tutorHomeVC.toDetialID = tmp.teacherId;
            
            [self.navigationController pushViewController:tutorHomeVC animated:YES];
        }
    }
}

#pragma mark --请求数据
/*
-(void)getListData
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForTeacherList] connectBlock:^(id data) {
        
        //方法一：不知道获取的数据是什么类型的，则用id类型进行接收，打印来看
        
        id result = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        //NSLog(@"result = %@",result);
        
        NSString *dataAgain = [result objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[dataAgain objectFromJSONString];
        
        //NSLog(@"jsonArray = %@",jsonArray);
        
        NSMutableArray *array = [NSMutableArray arrayWithArray:jsonArray];
        
        if (array.count != 0) {
            
            for (NSMutableDictionary *stuDic in array) {
                
                StudyPilotModel *pilotModel = [[StudyPilotModel alloc] init];
                
                pilotModel.iconUrl = [stuDic objectForKey:@"iconUrl"];
                pilotModel.level = [stuDic objectForKey:@"level"];
                pilotModel.likeNo = [stuDic objectForKey:@"likeNo"];
                pilotModel.name = [stuDic objectForKey:@"name"];
                pilotModel.position = [stuDic objectForKey:@"position"];
                pilotModel.serviceContent = [stuDic objectForKey:@"serviceContent"];
                pilotModel.serviceTitle = [stuDic objectForKey:@"serviceTitle"];
                pilotModel.teacherId = [[stuDic objectForKey:@"teacherId"] description];
                
                [self.studyPilotForHomeArray addObject:pilotModel];
                
            }
            
        }
        //NSLog(@"323233 %@", _studyPilotForHomeArray);
        [self.homeTableView reloadData];
        
    }];
}
*/
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
