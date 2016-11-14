/*
 * Copyright (c) 2014 杭州端点网络科技有限公司
 */

package com.tool.model;


import java.io.Serializable;
import java.util.Date;

/**
 * 订单信息
 * Created by yangzefeng on 14-9-4
 * Modified by xiao on 2014-11-7
 * Modified by haolin on 2014-12-08
 * @see io.terminus.ecp.trade.enums.Status
 * @see io.terminus.ecp.trade.enums.PaidType
 */
public class OrderDemo implements Serializable {

    private static final long serialVersionUID = -157253452712276812L;

    /**
     * ID
     */
    private Long id;

    /**
     * 流程实例ID
     */
    private Long fid;

    /**
     * 节点实例ID
     */
    private Long nid;

    /**
     * 主订单id，对一些服务商品会用到，如果本身是主订单，字段为null
     */
    private Long pid;
    /**
     * 买家ID
     */
    private Long buyerId;
    /**
     * 买家用户名
     */
    private String buyerName;
    /**
     * 买家联系电话
     */
    private String buyerMobile;
    /**
     * 卖家ID
     */
    private Long sellerId;
    /**
     * 卖家用户名
     */
    private String sellerName;
    /**
     * 店铺id
     */
    private Long shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 店铺行业ID
     */
    private Long businessId;

    /**
     * 店铺行业名称
     */
    private String businessName;
    /**
     * 订单状态
     * @see io.terminus.ecp.trade.enums.Status
     */
    private Integer status;
    /**
     * 订单类型
     * @see io.terminus.ecp.trade.enums.OrderType
     */
    private Integer type;
    /**
     * 订单运费
     */
    private Integer shipFee;
    /**
     * 订单总费用: originFee(订单原价, 即商品总价) + shipFee(运费) - discount(优惠)
     */
    private Integer fee;
    /**
     * 订单原价, 即商品总价
     */
    private Integer originFee;
    /**
     * 优惠(折扣)
     */
    private Integer discount;
    /**
     * 订单使用的账户余额
     */
    private Integer balance;
    /**
     * 订单使用的退款余额
     */
    private Integer refundBalance;
    /**
     * 支付类型
     * @see io.terminus.ecp.trade.enums.PaidType
     */
    private Integer payType;
    /**
     * 是否已支付
     */
    private Boolean hasPaid;
    /**
     * 是否已评价
     */
    private Boolean hasComment;

    /**
     * 是否已结算
     */
    private Boolean hasSettle;

    /**
     * 订单渠道
     */
    private Integer channel;

    /**
     * 订单来源
     */
    private String reference;

    /**
     * 来源id
     */
    private String referenceId;

    /**
     * 订单额外信息
     */
    private String  extra;

    /**
     * 外部订单号
     */
    private String outOrderId;

    /**
     * 外部配送单号
     */
    private String outDeliverNo;

    /**
     * 分布id，线下分布区域
     */
    private String orgId;

    private Date createdAt;

    private Date updatedAt;
    
    /**
     * 红包价
     */
    private Integer couponPrice;
    //使用店铺券金额
    private Integer shopCouponPrice;
    //使用平台券金额
    private Integer platCouponPrice;
    
    /***
     * 合单ID
     */
    private Long mergerId;
    
    /**
     * 是否需要发票
     */
    private Boolean hasInvoice;
    
    public Boolean hasPaid() {return hasPaid;}
    public Boolean hasComment() {return hasComment;}

    public enum CHANNEL {
        PC(0, "网页端"),         // 网页端
        MOBILE(1, "移动端"),     // 移动端，H5
        WECHAT(2, "微信端");     // 微信端

        public final int value;
        public final String desc;

        private CHANNEL(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFid() {
		return fid;
	}
	public void setFid(Long fid) {
		this.fid = fid;
	}
	public Long getNid() {
		return nid;
	}
	public void setNid(Long nid) {
		this.nid = nid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerMobile() {
		return buyerMobile;
	}
	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getShipFee() {
		return shipFee;
	}
	public void setShipFee(Integer shipFee) {
		this.shipFee = shipFee;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public Integer getOriginFee() {
		return originFee;
	}
	public void setOriginFee(Integer originFee) {
		this.originFee = originFee;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getRefundBalance() {
		return refundBalance;
	}
	public void setRefundBalance(Integer refundBalance) {
		this.refundBalance = refundBalance;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Boolean getHasPaid() {
		return hasPaid;
	}
	public void setHasPaid(Boolean hasPaid) {
		this.hasPaid = hasPaid;
	}
	public Boolean getHasComment() {
		return hasComment;
	}
	public void setHasComment(Boolean hasComment) {
		this.hasComment = hasComment;
	}
	public Boolean getHasSettle() {
		return hasSettle;
	}
	public void setHasSettle(Boolean hasSettle) {
		this.hasSettle = hasSettle;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	public String getOutDeliverNo() {
		return outDeliverNo;
	}
	public void setOutDeliverNo(String outDeliverNo) {
		this.outDeliverNo = outDeliverNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Integer getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(Integer couponPrice) {
		this.couponPrice = couponPrice;
	}
	public Integer getShopCouponPrice() {
		return shopCouponPrice;
	}
	public void setShopCouponPrice(Integer shopCouponPrice) {
		this.shopCouponPrice = shopCouponPrice;
	}
	public Integer getPlatCouponPrice() {
		return platCouponPrice;
	}
	public void setPlatCouponPrice(Integer platCouponPrice) {
		this.platCouponPrice = platCouponPrice;
	}
	public Long getMergerId() {
		return mergerId;
	}
	public void setMergerId(Long mergerId) {
		this.mergerId = mergerId;
	}
	public Boolean getHasInvoice() {
		return hasInvoice;
	}
	public void setHasInvoice(Boolean hasInvoice) {
		this.hasInvoice = hasInvoice;
	}
    
}
