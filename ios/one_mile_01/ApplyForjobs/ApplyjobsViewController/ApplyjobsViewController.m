//
//  ApplyjobsViewController.m
//  one_mile_01
//
//  Created by 王雅蓉 on 15/9/8.
//  Copyright (c) 2015年 王雅蓉. All rights reserved.
//

#import "ApplyjobsViewController.h"
#import "ApplyTableViewCell.h"
#import "ApplyModel.h"
#import "tutorHomeViewController.h"

@interface ApplyjobsViewController ()

@property (nonatomic, strong) NSMutableArray *applyArray;
@property (nonatomic, copy) NSString *applyFirstID;
@property (nonatomic, copy) NSString *applyLastID;
@property (nonatomic, assign) BOOL downUpdata;

@end

@implementation ApplyjobsViewController

-(instancetype)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    if (self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil]) {
        
        self.applyArray = [NSMutableArray array];
        self.applyFirstID = @"min";
        self.applyLastID = @"max";
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    
    [self setSubviews];
    
    [self getListData:self.applyFirstID withisFirst:YES];
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
    pilotTitleL.text = @"求职就业";
    pilotTitleL.font = [UIFont systemFontOfSize:18.0f];
    [self.view addSubview:pilotTitleL];
    
    self.applyTV = [[UITableView alloc] initWithFrame:CGRectMake(0, NAVIGATIONHEIGHT, WIDTH, HEIGHT - NAVIGATIONHEIGHT) style:UITableViewStylePlain];
    self.applyTV.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
    self.applyTV.showsVerticalScrollIndicator = NO;
    self.applyTV.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [self.view addSubview:self.applyTV];
    
    self.applyTV.dataSource = self;
    self.applyTV.delegate = self;
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
    if (_applyArray.count == 0) {
        
        return 0;
    }
    
    return _applyArray.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return COMMONCELLHEIGHT;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *applyCellIdentifier = @"applyCell";
    
    ApplyTableViewCell *applyCommonCell = [tableView dequeueReusableCellWithIdentifier:applyCellIdentifier];
    
    if (applyCommonCell == nil) {
        applyCommonCell = [[ApplyTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:applyCellIdentifier];
        applyCommonCell.selectionStyle = UITableViewCellSelectionStyleNone;
        applyCommonCell.backgroundColor = [UIColor colorWithRed:237 / 255.0 green:239 / 255.0 blue:240 / 255.0 alpha:1.0];
        //pilotCommonCell.backgroundColor = [UIColor yellowColor];
    }
    
    if (_applyArray.count == 0) {
        
        return applyCommonCell;
    }
    
    ApplyModel *tmp = [_applyArray objectAtIndex:indexPath.row];
    [applyCommonCell.applyTutorIV sd_setImageWithURL:[NSURL URLWithString:[tmp.iconUrl stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]] placeholderImage:[UIImage imageNamed:@"placeholders.png"]];
    
    applyCommonCell.applyTutorNameL.text = tmp.name;
    applyCommonCell.topicForApplyL.text = tmp.serviceTitle;
    
    if (tmp.position.length == 0) {
        
        if (tmp.major.length == 0) {
            
//            applyCommonCell.schoolForApplyL.text = tmp.simpleShow1;
//            applyCommonCell.positionForApplyL.text = tmp.simpleShow2;
            applyCommonCell.simInfoForApplyL.text = [NSString stringWithFormat:@"%@ %@", tmp.simpleShow1, tmp.simpleShow2];
        } else {
            
//            applyCommonCell.schoolForApplyL.text = tmp.schoolName;
//            applyCommonCell.positionForApplyL.text = tmp.position;
            applyCommonCell.simInfoForApplyL.text = [NSString stringWithFormat:@"%@ %@", tmp.schoolName, tmp.position];
        }
    } else {
        
//        applyCommonCell.schoolForApplyL.text = tmp.companyName;
//        applyCommonCell.positionForApplyL.text = tmp.position;
        applyCommonCell.simInfoForApplyL.text = [NSString stringWithFormat:@"%@ %@", tmp.companyName, tmp.position];
    }
    
    applyCommonCell.hasSeenForApplyL.text = [NSString stringWithFormat:@"%@人想见", tmp.likeNo];
    applyCommonCell.clickNumberForApplyL.text = [NSString stringWithFormat:@"本周可咨询%@次", tmp.timeperweek];
    
    return applyCommonCell;
}

#pragma mark --进入导师详情页面
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_applyArray.count == 0) {
        return;
    }
    tutorHomeViewController *tutorVC = [[tutorHomeViewController alloc] init];
    ApplyModel *tmp = [_applyArray objectAtIndex:indexPath.row];
    tutorVC.toDetialID = tmp.teacherId;
    tutorVC.applyM = tmp;
    tutorVC.serviceTitleStr = tmp.serviceTitle;
    [self.navigationController pushViewController:tutorVC animated:YES];
}

#pragma mark --请求数据
-(void)getListData:(NSString *)userId withisFirst:(BOOL)isFirst
{
    NSString *urlStr = @"http://service.1yingli.cn/yiyingliService/manage";
    
    [AFNConnect AFNConnectWithUrl:urlStr withBodyData:[AFNConnect createDataForList:@"2" withNextID:userId withdownUpdata:_downUpdata withisFirst:isFirst] connectBlock:^(id data) {
        
        //方法一：不知道获取的数据是什么类型的，则用id类型进行接收，打印来看
        
        id result = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        //NSLog(@"==========result = %@",result);
        
        NSString *dataAgain = [result objectForKey:@"data"];
        
        NSArray *jsonArray = (NSArray *)[dataAgain objectFromJSONString];
        
        for (NSDictionary *dic in jsonArray) {
            
            ApplyModel *applyM = [[ApplyModel alloc] init];
            
            applyM.iconUrl = [dic objectForKey:@"iconUrl"];
            applyM.level = [dic objectForKey:@"level"];
            applyM.likeNo = [dic objectForKey:@"likeNo"];
            applyM.name = [dic objectForKey:@"name"];
            applyM.serviceTitle = [dic objectForKey:@"serviceTitle"];
            applyM.serviceContent = [dic objectForKey:@"serviceContent"];
            applyM.simpleShow1 = [dic objectForKey:@"simpleShow1"];
            applyM.simpleShow2 = [dic objectForKey:@"simpleShow2"];
            applyM.teacherId = [dic objectForKey:@"teacherId"];
            applyM.companyName = [dic objectForKey:@"companyName"];
            applyM.position = [dic objectForKey:@"position"];
            applyM.schoolName = [dic objectForKey:@"schoolName"];
            applyM.major = [dic objectForKey:@"major"];
            applyM.timeperweek = [dic objectForKey:@"timeperweek"];
            
            [self.applyArray addObject:applyM];
        }
        
        [self.applyTV reloadData];
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
