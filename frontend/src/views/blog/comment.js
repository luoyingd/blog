import {
  Avatar,
  Button,
  List,
  Skeleton,
  Modal,
  Popconfirm,
  Form,
  Spin,
  Radio,
  Card,
  message,
} from "antd";
import { myUser } from "../../utils/auth";
import TextArea from "antd/es/input/TextArea";
import { useEffect, useState } from "react";
import modalStore from "../../stores/common/modalStore";
import { observer } from "mobx-react-lite";
import commentStore from "../../stores/blogs/commentStore";
import { baseURL } from "../../utils/http";
import "./index.scss";
function Comments({ blogId }) {
  const [index, setIndex] = useState(1);
  const [loading, setLoading] = useState(true);
  const [key, setKey] = useState(1);
  useEffect(() => {
    commentStore.loadComment(index, blogId);
    setLoading(false);
  }, []);
  const onChangeMine = (e) => {
    setLoading(true);
    commentStore.changeMine(e.target.value, blogId);
    setIndex(1);
    setLoading(false);
  };
  const onLoadMore = async () => {
    setLoading(true);
    await commentStore.loadComment(index + 1, blogId);
    setIndex(index + 1);
    setLoading(false);
    window.dispatchEvent(new Event("resize"));
  };
  const onAddComment = (e) => {
    commentStore.content = e.target.value;
  };
  const onAddReply = (replierId) => {
    commentStore.replierId = replierId;
    modalStore.isModalOpen = true;
  };
  const onSubmitComment = async () => {
    setLoading(true);
    await commentStore.submitComment(blogId);
    modalStore.isModalOpen = false;
    commentStore.loadComment(1, blogId);
    setIndex(1);
    setLoading(false);
    message.success("Comment successfully!");
  };
  const onConfirmDelete = async (id) => {
    setLoading(true);
    console.log(id);
    await commentStore.deleteComment(id, blogId);
    commentStore.loadComment(1, blogId);
    setIndex(1);
    setLoading(false);
    message.success("Delete successfully!");
  };
  const loadMore = !loading ? (
    <div
      style={{
        textAlign: "center",
        marginTop: 12,
        height: 32,
        lineHeight: "32px",
      }}
    >
      <Button onClick={onLoadMore}>load more</Button>
    </div>
  ) : null;
  return (
    <div>
      <Card
        style={{
          marginTop: 20,
        }}
        className="title-card"
        type="inner"
        title={
          <div>
            Comments
            <Radio.Group
              onChange={onChangeMine}
              value={commentStore.isMine}
              style={{
                marginLeft: "20px",
              }}
            >
              <Radio value={false}>All</Radio>
              <Radio value={true}>Mine</Radio>
            </Radio.Group>
          </div>
        }
      >
        <Button
          onClick={() => {
            modalStore.isModalOpen = true;
            commentStore.content = null;
            commentStore.replierId = 0;
            setKey(key + 1); // enforce re-render
          }}
        >
          Add Comment
        </Button>
        <List
          className="demo-loadmore-list"
          style={{ marginTop: "5px" }}
          loading={loading}
          itemLayout="horizontal"
          loadMore={loadMore}
          dataSource={commentStore.list}
          renderItem={(item) => (
            <List.Item
              actions={[
                <Button
                  type="link"
                  onClick={() => {
                    setKey(key + 1);
                    onAddReply(item.senderId);
                  }}
                >
                  reply
                </Button>,
                myUser.getUserId() == item.senderId ? (
                  <Popconfirm
                    placement="topRight"
                    onConfirm={() => {
                      onConfirmDelete(item.commentId);
                    }}
                    title="Sure to delete?"
                    okText="Yes"
                    cancelText="Cancel"
                  >
                    <a key="list-loadmore-delete">delete</a>
                  </Popconfirm>
                ) : null,
              ]}
            >
              <Skeleton avatar title={false} loading={loading} active>
                <List.Item.Meta
                  avatar={
                    <Avatar
                      src={baseURL + "/common/photo/" + item.senderPhoto}
                    />
                  }
                  title={
                    item.replierName
                      ? `${item.senderName} replied to ${item.replierName}`
                      : item.senderName
                  }
                  description={
                    <div className="comment-content">{item.content}</div>
                  }
                />
              </Skeleton>
              <div className="comment-time">{item.createTime}</div>
            </List.Item>
          )}
        />
      </Card>
      <Modal
        title="Add Comment"
        open={modalStore.isModalOpen}
        key={key}
        onOk={onSubmitComment}
        onCancel={() => {
          modalStore.isModalOpen = false;
        }}
      >
        <Form autoComplete="off">
          <Form.Item label="Content">
            <TextArea
              rows={3}
              placeholder="No more than 200 words!"
              onChange={onAddComment}
              maxlength="200"
            />
            <Spin spinning={loading}></Spin>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
}

export default observer(Comments);
