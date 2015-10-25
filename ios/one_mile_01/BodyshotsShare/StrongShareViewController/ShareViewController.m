//
//  ShareViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "ShareViewController.h"
#import "ShareTableViewCell.h"
#import "ShareModel.h"
#import "tutorHomeViewController.h"

@interface ShareViewController ()

@property (nonatomic, strong) NSMutableArray *shareArray;
@property (nonatomic, copy) NSString *shareFirstID;
@property (nonatomic, copy) NSString *shareLastID;
@property (nonatomic, assign) BOOL downUpdata;

@end

@implementation ShareViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.shareArray = [NSMutableArray array];
        self.shareFirstID = @"min";
        self.shareLastID = @"max";
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    
    [self setSubviews];
    
    self.downUpdata = YES;
    [self getListData:self.shareFirstID withisFirst:YES];
}

-(void)setSubviews
{
    UIButton *pushBT = [UIButton buttonWithType:UIButtonTypeCustom];
    pushBT.frame = CGRectMake(20, 40, PUSHANDPOPBUTTONSIZE, PUSHANDPOPBUTTONSIZE);
    //[pushBT setBackgroundColor:[UIColor blackColor]];
    [pushBT setBackgroundImage:[UIImage imageNamed:@"pop_dark.png"] forState:UIControlStateNormal];
    [pushBT addTarget:self action:@selector(popToRoot:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:pushBT];
    
    UILabel *pilotTitleL = [[UILabel alloc] initWithFrame:CGRectMake(WIDTH / 2.0 - 40, pushBT.frame.origin.y, 80, pushBT.frame.size.height)];
    pilotTitleL.text = @"猎奇分享";
    pilotTitleL.font = [UIFont systemFontOfSize:18.0f];
    [self.view addSubview:pilotTitleL];
    
    self.shareTV = [[UITableView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT) style:UITableViewStylePlain];
    self.shareTV.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.shareTV.showsVerticalScrollIndicator = NO;
    self.shareTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [self.view addSubview:self.shareTV];
    
    self.shareTV.dataSource = self;
    self.shareTV.delegate = self;
}

-(void)popToRoot:(UIButton *)button
{
    [self.navigationController popToRootViewControllerAnimated:YES];
}

#pragma mark -- viewWillAppear & viewWillDisappear
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
}

-(void)viewDidDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    
    self.navigationController.navigationBarHidden = YES;
    self.tabBarController.tabBar.hidden = YES;
    
}

#pragma mark -- tableviewDelegate
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (_shareArray.count == 0) {
        
        return 0;
    }
    
    return _shareArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *shareCellIdentifier = @"shareCell";
    
    ShareTableViewCell *shareCommonCell = [tableView dequeueReusableCellWithIdentifier:shareCellIdentifier];
    
    if (shareCommonCell == nil) {
        shareCommonCell = [[ShareTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:shareCellIdentifier];
        shareCommonCell.selectionStyle = UITableViewCellSelectionStyleNone;
        shareCommonCell.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
        //pilotCommonCell.backgroundColor = [UIColor yellowColor];
    }
    
    if (_shareArray.count == 0) {
        
        return shareCommonCell;
    }
    
    ShareModel *tmp = [_shareArray objectAtIndex:indexPath.row];
    [shareCommonCell.shareTutorIV sd_setImageWithURL:[NSURL URLWithString:[tmp.iconUrl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    
    shareCommonCell.shareTutorNameL.text = tmp.name;
    shareCommonCell.topicForShareL.text = tmp.serviceTitle;
    
    if (tmp.position.length == 0) {
        
        if (tmp.major.length == 0) {
            
//            shareCommonCell.schoolForShareL.text = tmp.simpleShow1;
//            shareCommonCell.positionForShareL.text = tmp.simpleShow2;
            shareCommonCell.simInfoForShareL.text = [NSString stringWithFormat:@"%@ %@", tmp.simpleShow1, tmp.simpleShow2];
        } else {
        
//            shareCommonCell.schoolForShareL.text = tmp.schoolName;
//            shareCommonCell.positionForShareL.text = tmp.major;
            shareCommonCell.simInfoForShareL.text = [NSString stringWithFormat:@"%@ %@", tmp.schoolName, tmp.major];
        }
    } else {
    
//        shareCommonCell.schoolForShareL.text = tmp.companyName;
//        shareCommonCell.positionForShareL.text = tmp.position;
        shareCommonCell.simInfoForShareL.text = [NSString stringWithFormat:@"%@ %@", tmp.companyName, tmp.position];
    }
    
    shareCommonCell.hasSeenForShareL.text = [NSString stringWithFormat:@"%@ 人想见", tmp.likeNo];
    shareCommonCell.clickNumberForShareL.text = [NSString stringWithFormat:@"本周可咨询%@次", tmp.timeperweek];
    
    return shareCommonCell;
}

#pragma mark --进入导师详情页面
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_shareArray.count == 0) {
        return;
    }
    tutorHomeViewController *tutorVC = [[tutorHomeViewController alloc] init];
    ShareModel *tmp = [_shareArray objectAtIndex:indexPath.row];
    tutorVC.toDetialID = tmp.teacherId;
    tutorVC.serviceTitleStr = tmp.serviceTitle;
    tutorVC.shareM = tmp;
    [self.navigationController pushViewController:tutorVC animated:YES];
}

#pragma mark --请求数据
-(void)getListData:(NSString *)userId withisFirst:(BOOL)isFirst
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForList:@"16" withNextID:userId withdownUpdata:_downUpdata withisFirst:isFirst] connectBlock:^(id data) {
        
        //方法一：不知道获取的数据是什么类型的，则用id类型进行接收，打印来看
        
        id result = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        //NSLog(@"==========result = %@",result);
        
        NSString *dataAgain = [result objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[dataAgain objectFromJSONString];
        //NSLog(@"%@",jsonArray);
        
        for (NSDictionary *dic in jsonArray) {
            
            ShareModel *shareM = [[ShareModel alloc] init];
            
            shareM.iconUrl = [dic objectForKey:@"iconUrl"];
            shareM.level = [dic objectForKey:@"level"];
            shareM.likeNo = [dic objectForKey:@"likeNo"];
            shareM.name = [dic objectForKey:@"name"];
            shareM.serviceTitle = [dic objectForKey:@"serviceTitle"];
            shareM.serviceContent = [dic objectForKey:@"serviceContent"];
            shareM.simpleShow1 = [dic objectForKey:@"simpleShow1"];
            shareM.simpleShow2 = [dic objectForKey:@"simpleShow2"];
            shareM.teacherId = [dic objectForKey:@"teacherId"];
            shareM.companyName = [dic objectForKey:@"companyName"];
            shareM.position = [dic objectForKey:@"position"];
            shareM.schoolName = [dic objectForKey:@"schoolName"];
            shareM.major = [dic objectForKey:@"major"];
            shareM.timeperweek = [dic objectForKey:@"timeperweek"];
            
            [self.shareArray addObject:shareM];
        }
        
        [self.shareTV reloadData];
    }];
}

-(void)showAlert:(NSString *)message
{
    UIAlertView *promptAlert = [[UIAlertView alloc] initWithTitle:@"提示" message:message delegate:self cancelButtonTitle:nil otherButtonTitles:nil];
    
    [NSTimer scheduledTimerWithTimeInterval:0.8f target:self selector:@selector(timerFireMethod:) userInfo:promptAlert repeats:YES];
    
    [promptAlert show];
}

-(void)timerFireMethod:(NSTimer *)theTimer
{
    UIAlertView *promptAlert = (UIAlertView *)[theTimer userInfo];
    [promptAlert dismissWithClickedButtonIndex:0 animated:NO];
    promptAlert = NULL;
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
