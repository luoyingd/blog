import { Breadcrumb } from "antd";
import { Link } from "react-router-dom";
const BreadTop = ({ previous, now }) => {
  return (
    <Breadcrumb separator=">">
      {previous.map((item) => {
        return (
          <Breadcrumb.Item key={item.link}>
            <Link to={item.link}>{item.name}</Link>
          </Breadcrumb.Item>
        );
      })}

      <Breadcrumb.Item>{now}</Breadcrumb.Item>
    </Breadcrumb>
  );
};

export default BreadTop;
