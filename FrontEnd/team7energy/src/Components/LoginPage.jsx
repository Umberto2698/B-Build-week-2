import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { EMAIL_VALIDATION, PASSWORD_VALIDATION, fetchLogin } from "../Redux/Actions/LoginActions";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const loginState = useSelector((state) => state.login);
  useEffect(() => {
    if (loginState.respLogin.authorizationToken) {
      navigate("/homepage");
    }
  }, [loginState.respLogin.authorizationToken]);
  return (
    <Container className="p-4 h-100">
      <Row className="h-100">
        <Col className="d-flex flex-column align-items-center justify-content-center h-100">
          <Card className="w-25">
            <Card.Body>
              <Form
                onSubmit={(e) => {
                  e.preventDefault();
                  dispatch(fetchLogin(loginState.validationContent.email, loginState.validationContent.password));
                }}
              >
                <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label>Email address</Form.Label>
                  <Form.Control
                    type="email"
                    placeholder="Enter email"
                    value={loginState.validationContent.email}
                    onChange={(e) => {
                      dispatch({ type: EMAIL_VALIDATION, payload: e.target.value });
                    }}
                  />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicPassword">
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    type="password"
                    placeholder="Password"
                    value={loginState.validationContent.password}
                    onChange={(e) => {
                      dispatch({ type: PASSWORD_VALIDATION, payload: e.target.value });
                    }}
                  />
                </Form.Group>
                <Button variant="primary" type="submit">
                  Submit
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};
export default LoginPage;
