import {
  Card,
  Form,
  Button,
  Radio,
  Input,
  Space,
  Select,
  message,
  Image,
} from "antd";
import TextArea from "antd/es/input/TextArea";
import { topics } from "../../utils/constant";
import MyQuill from "../../components/myQuill";
import "react-quill/dist/quill.snow.css";
import BreadTop from "../../components/breadTop";
import { useSearchParams } from "react-router-dom";
import { observer } from "mobx-react-lite";
import "./index.scss";
import { useEffect, useState } from "react";
import blogStore from "../../stores/blogs/blogStore";
import MyUpload from "../../components/upload/myUpload";
import fileStore from "../../stores/common/fileStore";
import { baseURL } from "../../utils/http";
import quillStore from "../../stores/blogs/quillStore";
import uploadStore from "../../stores/common/uploadStore";
const { Option } = Select;
const Publish = () => {
  const [hasCover, setHasCover] = useState(false);
  const [coverKey, setCoverKey] = useState(null);
  const [quillKey, setQuillKey] = useState(0);
  const [params] = useSearchParams();
  const id = params.get("id");
  const [form] = Form.useForm();
  const radioChange = (e) => {
    setHasCover(e.target.value);
  };
  const afterUpload = (keys) => {
    console.log("Receive file upload keys: ", keys);
    setCoverKey(keys[0]);
  };
  const handleUpload = async () => {
    const finish = fileStore.checkFile(fileStore.coverFileList);
    if (finish === true) {
      console.log("Start uploading");
      fileStore.uploadFiles(afterUpload, fileStore.coverFileList);
      fileStore.coverFileList = [];
    } else {
      message.error(
        "You can only upload JPG/PNG/JPEG file, and each file should be smaller than 2MB!"
      );
    }
  };
  const onFinish = async (values) => {
    if (coverKey && hasCover) {
      values.cover = coverKey;
    }
    if (id) {
      values.id = id;
    }
    await blogStore.addOrUpdateBlog(values);
    message.success("Publish successfully!");
  };
  const loadDetail = async () => {
    const data = await blogStore.loadBlogDetail(id);
    form.setFieldsValue({ topic: data.topic });
    form.setFieldsValue({ title: data.title });
    form.setFieldsValue({ introduction: data.introduction });
    quillStore.content = data.content;
    setQuillKey(quillKey + 1); // enforce re-render
    if (data.cover) {
      form.setFieldsValue({ hasCover: true });
      setHasCover(true);
      setCoverKey(data.cover);
    }
  };
  useEffect(() => {
    quillStore.content = null;
    setQuillKey(quillKey + 1); // enforce re-render
    if (id) {
      loadDetail();
    }
  }, []);

  const UploadCover = () => {
    uploadStore.fileType = 1;
    return (
      <div>
        <MyUpload maxCount={1}></MyUpload>
        <Button
          type="primary"
          style={{ marginTop: "10px" }}
          onClick={handleUpload}
        >
          OK
        </Button>
      </div>
    );
  };

  return (
    <div className="publish">
      <Card
        title={
          <BreadTop
            previous={[
              { link: "/home", name: "Home" },
              { link: "/home/allBlog", name: "AllBlogs" },
            ]}
            now="Publish"
          ></BreadTop>
        }
        style={{ marginBottom: 20 }}
      >
        <Card title={id ? "Edit Blog" : "Publish Blog"}>
          <Form
            labelCol={{ span: 4 }}
            wrapperCol={{ span: 16 }}
            initialValues={{ type: false, content: "", hasCover: false }}
            onFinish={onFinish}
            form={form}
          >
            <Form.Item
              label="Title"
              name="title"
              rules={[{ required: true, message: "Title is required" }]}
            >
              <Input placeholder="Please input title" style={{ width: 400 }} />
            </Form.Item>
            <Form.Item
              label="Topic"
              name="topic"
              rules={[{ required: true, message: "Topic is required" }]}
            >
              <Select placeholder="Please select topic" style={{ width: 400 }}>
                {topics
                  .filter((item) => item !== "All")
                  .map((item) => {
                    return (
                      <Option key={item} value={item}>
                        {item}
                      </Option>
                    );
                  })}
              </Select>
            </Form.Item>
            <Form.Item
              label="Introduction"
              name="introduction"
              rules={[{ required: true, message: "Introduction is required" }]}
            >
              <TextArea
                rows={3}
                placeholder="No more than 200 words!"
                maxlength="200"
              />
            </Form.Item>
            <Form.Item label="Cover">
              <Form.Item name="hasCover">
                <Radio.Group onChange={radioChange}>
                  <Radio value={true}>With Cover</Radio>
                  <Radio value={false}>No Cover</Radio>
                </Radio.Group>
              </Form.Item>
              {hasCover ? (
                !coverKey ? (
                  <UploadCover></UploadCover>
                ) : (
                  <div>
                    <Image
                      width={200}
                      src={baseURL + "/common/photo/" + coverKey}
                    />
                    <UploadCover></UploadCover>
                  </div>
                )
              ) : null}
            </Form.Item>
            <Form.Item label="Content" name="content">
              <MyQuill className="quill" key={quillKey}></MyQuill>
            </Form.Item>
            <Form.Item wrapperCol={{ offset: 4 }}>
              <Space>
                <Button size="large" type="primary" htmlType="submit">
                  Submit
                </Button>
              </Space>
            </Form.Item>
          </Form>
        </Card>
      </Card>
    </div>
  );
};

export default observer(Publish);
