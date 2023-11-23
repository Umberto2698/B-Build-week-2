import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";

const LoginPage = () => {
  return (
    <Container className="p-4 h-100">
      <Row className="h-100">
        <Col className="d-flex flex-column align-items-center justify-content-center h-100">
          <Card className="w-25">
            <Card.Body>
              <Form>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label>Email address</Form.Label>
                  <Form.Control type="email" placeholder="Enter email" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicPassword">
                  <Form.Label>Password</Form.Label>
                  <Form.Control type="password" placeholder="Password" />
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
