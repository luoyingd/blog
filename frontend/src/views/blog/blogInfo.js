import "./index.scss";
import { Card, Button, Tag, Radio, Avatar } from "antd";
import { LikeOutlined } from "@ant-design/icons";
import Comments from "./comment";
import { observer } from "mobx-react-lite";
import BreadTop from "../../components/breadTop";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import blogStore from "../../stores/blogs/blogStore";
import { baseURL } from "../../utils/http";
import "react-quill/dist/quill.snow.css";
function BlogInfo() {
  const params = useParams();
  useEffect(() => {
    // 加载文章信息
    blogStore.loadBlogContent(params.id);
    blogStore.loadLikeStatus(params.id);
  }, []);
  const onChangeLike = () => {
    blogStore.likeNow = !blogStore.likeNow;
    blogStore.alterLikeStatus(params.id);
  };
  return (
    <div>
      <Card
        title={
          <BreadTop
            previous={[
              { link: "/home", name: "Home" },
              { link: "/home/allBlog", name: "AllBlogs" },
            ]}
            now="BlogInfo"
          ></BreadTop>
        }
        style={{ marginBottom: 20 }}
      >
        <Card
          title={<h1 class="title-article">{blogStore.myBlog.title}</h1>}
          type="inner"
        >
          <div className="article-info-box">
            <div className="article-bar-top">
              <Avatar
                size="medium"
                className="article-type-img"
                src={baseURL + "/common/photo/" + blogStore.myBlog.authorPhoto}
              />
              <div className="bar-content">
                <span className="follow-nickName">
                  {blogStore.myBlog.author}
                </span>
                <span class="label">Topic: </span>
                <Tag className="tag">{blogStore.myBlog.topic}</Tag>
                <span className="time">
                  published on {blogStore.myBlog.createTime}
                </span>
              </div>
            </div>
          </div>
          <Card
            style={{
              marginTop: 20,
            }}
            className="title-card"
            type="inner"
            title={
              <div>
                Content
                <Button
                  className={blogStore.likeNow ? "like" : "unlike"}
                  shape="circle"
                  size="small"
                  icon={<LikeOutlined />}
                  onClick={onChangeLike}
                />
                <span className="like-count">{blogStore.myBlog.likes}</span>
              </div>
            }
          >
            <div
              class="ql-editor"
              dangerouslySetInnerHTML={{ __html: blogStore.myBlog.content }}
            ></div>
          </Card>
        </Card>
        <Comments blogId={params.id}></Comments>
      </Card>
    </div>
  );
}
export default observer(BlogInfo);
