package cn.yiyingli.Handle.Service;

import java.util.List;

import cn.yiyingli.ExchangeData.SuperMap;
import cn.yiyingli.ExchangeData.Util.ExArrayList;
import cn.yiyingli.ExchangeData.Util.ExList;
import cn.yiyingli.Handle.MsgService;
import cn.yiyingli.Persistant.Comment;
import cn.yiyingli.Persistant.ServicePro;
import cn.yiyingli.Service.CommentService;
import cn.yiyingli.Service.ServiceProService;
import cn.yiyingli.Util.MsgUtil;

public class FGetCommentListByServiceProService extends MsgService {

	private ServiceProService serviceProService;

	private CommentService commentService;

	public ServiceProService getServiceProService() {
		return serviceProService;
	}

	public void setServiceProService(ServiceProService serviceProService) {
		this.serviceProService = serviceProService;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@Override
	protected boolean checkData() {
		return getData().containsKey("serviceProId") && getData().containsKey("page");
	}

	@Override
	public void doit() {
		ServicePro servicePro = getServiceProService().query(getData().getLong("serviceProId"));
		if (servicePro == null) {
			setResMsg(MsgUtil.getErrorMsgByCode("22008"));
			return;
		}
		int page = 0;
		SuperMap toSend = MsgUtil.getSuccessMap();
		long count = servicePro.getCommentNo();
		toSend.put("count", count);
		try {
			page = Integer.parseInt((String) getData().get("page"));
		} catch (Exception e) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		if (page <= 0) {
			setResMsg(MsgUtil.getErrorMsgByCode("22005"));
			return;
		}
		List<Comment> comments = getCommentService().queryListByServiceProId(servicePro.getId(), page,
				CommentService.COMMENT_KIND_FROMUSER_SHORT);
		ExList sends = new ExArrayList();
		for (Comment c : comments) {
			SuperMap map = new SuperMap();
			map.put("commentId", c.getId());
			map.put("content", c.getContent());
			map.put("score", c.getScore());
			map.put("createTime", c.getCreateTime());
			map.put("nickName", c.getUser().getNickName());
			map.put("iconUrl", c.getUser().getIconUrl());
			map.put("serviceTitle", c.getServiceTitle());
			sends.add(map.finish());
		}
		setResMsg(toSend.put("data", sends).finishByJson());
	}

}
