import {
  Table,
  Input,
  Card,
  Form,
  Button,
  Radio,
  DatePicker,
  Select,
  Spin,
  Popconfirm,
  Space,
  Image,
  message,
} from "antd";
import BreadTop from "../../components/breadTop";
import { topics } from "../../utils/constant";
import userStore from "../../stores/user/userStore";
import { useEffect, useState } from "react";
import { observer } from "mobx-react-lite";
import { useNavigate } from "react-router-dom";
import loadingStore from "../../stores/common/loadingStore";
import pageStore from "../../stores/common/pageStore";
import blogStore from "../../stores/blogs/blogStore";
import { LikeOutlined, CommentOutlined } from "@ant-design/icons";
import { baseURL } from "../../utils/http";
function Blog() {
  const navigator = useNavigate();
  const { RangePicker } = DatePicker;
  const [isMine, setIsMine] = useState(false);
  const [values, setValues] = useState({
    authorId: null,
    topic: null,
    startDate: null,
    endDate: null,
    page: pageStore.page,
    pageSize: pageStore.pageSize,
    title: null,
  });
  const baseColumn = [
    {
      title: "Title",
      dataIndex: "title",
      key: "title",
      width: 160,
      fixed: "left",
      render: (_, record) => (
        <a href={`/home/allBlog/info/${record.key}`}>{record.title}</a>
      ),
    },
    {
      title: "Author",
      dataIndex: "author",
      key: "author",
      width: 80,
      fixed: "left",
    },
    {
      title: "Topic",
      dataIndex: "topic",
      key: "topic",
      width: 100,
    },
    {
      title: "Cover",
      dataIndex: "cover",
      key: "cover",
      width: 100,
      render: (_, record) => {
        return record.cover ? (
          <Image width={150} src={baseURL + "/common/photo/" + record.cover} />
        ) : null;
      },
    },
    {
      title: "Introduction",
      dataIndex: "introduction",
      key: "introduction",
      width: 500,
    },
    {
      title: "Feedback",
      dataIndex: "feedback",
      key: "feedback",
      width: 70,
      render: (_, record) => (
        <div>
          <LikeOutlined />
          <span style={{ marginLeft: "2px" }}>{record.likes}</span>
          <CommentOutlined style={{ marginLeft: "12px" }} />
          <span style={{ marginLeft: "2px" }}>{record.comments}</span>
        </div>
      ),
    },
    {
      title: "Create Time",
      dataIndex: "createTime",
      key: "createTime",
      width: 100,
    },
  ];
  const [columns, setColumns] = useState(baseColumn);
  const editColumn = {
    title: "Operation",
    dataIndex: "operation",
    width: 100,
    key: "operation",
    render: (_, record) =>
      blogStore.blogs.length >= 1 ? (
        <Space size="middle">
          <Popconfirm
            title="Sure to delete?"
            onConfirm={() => handleDelete(record.key)}
          >
            <a>Delete</a>
          </Popconfirm>
          <Space>
            <a onClick={() => handleEdit(record.key)}>Edit</a>
          </Space>
        </Space>
      ) : null,
  };
  const pageChange = async (page, size) => {
    pageStore.pageChange(page, size);
    await blogStore.loadBlogs(values);
  };
  const handleEdit = (key) => {
    navigator("/home/publish?id=" + key);
  };
  const handleDelete = async (key) => {
    await blogStore.delete(key);
    message.success("Successfully deleted!");
    onFinish({ mine: true });
  };
  const onChangeMine = (e) => {
    setIsMine(e.target.value);
  };
  const onFinish = async (values) => {
    await blogStore.loadBlogCount(values);
    await blogStore.loadBlogs(values);
    if (values.mine) {
      setColumns([...baseColumn, editColumn]);
    } else {
      setColumns(baseColumn);
    }
    setValues(values);
  };
  useEffect(() => {
    userStore.loadAllUser();
    blogStore.loadBlogCount(values);
    blogStore.loadBlogs(values);
  }, []);
  return (
    <div>
      <Card
        title={
          <BreadTop
            previous={[{ link: "/home", name: "Home" }]}
            now="AllBlogs"
          ></BreadTop>
        }
        style={{ marginBottom: 20 }}
      >
        <Form onFinish={onFinish} initialValues={{ topic: "All", mine: false }}>
          <Form.Item label="IsMine" name="mine">
            <Radio.Group style={{ display: "flex" }} onChange={onChangeMine}>
              <Radio value={true}>Mine</Radio>
              <Radio value={false}>All</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item label="Topics" name="topic">
            <Radio.Group style={{ display: "flex" }}>
              {topics.map((topic) => {
                return (
                  <Radio value={topic} key={topic}>
                    {topic}
                  </Radio>
                );
              })}
            </Radio.Group>
          </Form.Item>
          <Form.Item label="Title" name="title" style={{ display: "flex" }}>
            <Input allowClear></Input>
          </Form.Item>
          <Form.Item
            label="Authors"
            name="authorId"
            style={{ display: "flex" }}
          >
            <Select
              placeholder="Please select an author"
              style={{ width: 200 }}
              showSearch
              filterOption={(input, option) =>
                (option?.label ?? "")
                  .toLowerCase()
                  .includes(input.toLowerCase())
              }
              allowClear
              disabled={isMine}
              options={userStore.allUserIdAndName.map((user) => {
                return { value: user.id, label: user.username };
              })}
            ></Select>
          </Form.Item>
          <Form.Item
            label="Create Time"
            name="date"
            style={{ display: "flex" }}
          >
            <RangePicker />
          </Form.Item>
          <Form.Item style={{ display: "flex" }}>
            <Button
              type="primary"
              htmlType="submit"
              disabled={loadingStore.isLoading}
            >
              Search
            </Button>
            <Spin spinning={loadingStore.isLoading}></Spin>
          </Form.Item>
        </Form>
      </Card>
      <Card title={`Total ${pageStore.total} Blogs`}>
        <Table
          rowKey="id"
          columns={columns}
          dataSource={blogStore.blogs}
          pagination={{
            pageSize: pageStore.pageSize,
            total: pageStore.total,
            onChange: pageChange,
            current: pageStore.page,
          }}
          bordered
          scroll={{
            x: 2000,
          }}
        />
        <Spin spinning={loadingStore.isLoading}></Spin>
      </Card>
    </div>
  );
}

export default observer(Blog);
