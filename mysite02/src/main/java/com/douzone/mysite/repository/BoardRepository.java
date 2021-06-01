package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestbookVo;

public class BoardRepository {
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://192.168.80.112:3307/webdb?characterEncoding=utf8";
			String id = "webdb";
			String password = "webdb";
			conn = DriverManager.getConnection(url, id, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error" + e);
		}

		return conn;
	}

	public List<BoardVo> findByPage(int pageNum) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select a.no, a.title, a.depths, a.hit, b.no , b.name, a.reg_date, a.parent_no " + "from board a, user b "
					+ "where a.user_no = b.no " + "order by a.group_no DESC, a.order_no ASC " + "limit ?,5 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ((pageNum - 1) * 5));
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				int depths = rs.getInt(3);
				int hit = rs.getInt(4);
				Long userNo = rs.getLong(5);
				String userName = rs.getString(6);
				String regDate = rs.getString(7).replace(".0", "");
				Long parentNo = rs.getLong(8);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setDepth(depths);
				vo.setHit(hit);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setRegDate(regDate);
				vo.setParentNo(parentNo);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
	}

	public double findTotalPage() {
		double result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select count(*) " + "from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
	}

	public int findMaxGroupNo() {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select max(group_no) " + "from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
	}

	public boolean insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "insert into board " + "values(null,?,?,sysdate(),0,?,?,?,?,?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getGroupNo());
			pstmt.setInt(4, vo.getOrderNo());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());
			pstmt.setLong(7, vo.getParentNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
	}

	public BoardVo findByNo(Long boardNo) {
		BoardVo result = new BoardVo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select a.no, a.title, a.contents, a.reg_date, a.hit, a.group_no, a.order_no, a.depths,b.no, b.name "
					+ "from board a, user b "
					+ "where a.user_no = b.no "
					+ "and a.no=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				String regDate = rs.getString(4).replace(".0", "");
				int hit = rs.getInt(5);
				int groupNo = rs.getInt(6);
				int orderNo = rs.getInt(7);
				int depths = rs.getInt(8);
				Long userNo = rs.getLong(9);
				String userName = rs.getString(10);
				

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setRegDate(regDate);
				vo.setHit(hit);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depths);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				
				result=vo;
			}

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;

	}

	public boolean updateHit(Long boardNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "update board "
					+ "set hit=hit+1 "
					+ "where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, boardNo);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
	}

	public boolean update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "update board "
					+ "set title=?, contents=?, reg_date=sysdate() "
					+ "where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
		
	}

	public boolean delete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "delete from board "
					+ "where no=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
		
	}

	public boolean reply(int groupNo, int orderNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "update board "
					+ "set order_no=?+1 "
					+ "where group_no=? and order_no>=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, orderNo);
			pstmt.setInt(2, groupNo);
			pstmt.setInt(3, orderNo);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
		
	}

	public List<BoardVo> search(String search, int pageNum) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select distinct a.no, a.title, a.depths, a.hit, a.user_no,b.name, a.reg_date "
					+ "from board a "
					+ "join user b on a.user_no = b.no  "
					+ "where a.title like ? or a.contents like ?  "
					+ "order by a.group_no DESC, a.order_no ASC "
					+ "limit ?,5";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setString(2, "%"+search+"%");
			pstmt.setInt(3, ((pageNum - 1) * 5));
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				int depths = rs.getInt(3);
				int hit = rs.getInt(4);
				Long userNo = rs.getLong(5);
				String userName = rs.getString(6);
				String regDate = rs.getString(7).replace(".0", "");

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setDepth(depths);
				vo.setHit(hit);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setRegDate(regDate);

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
	}
	
	public double findSearchTotalPage(String search) {
		double result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select count(*) "
					+ " from (select distinct a.no, a.title, a.depths, a.hit, a.user_no , b.name, a.reg_date "
					+ " from board a "
					+ " join user b on a.user_no = b.no "
					+ " where a.title like ? or a.contents like ?)c ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setString(2, "%"+search+"%");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
	}

	public boolean updateDelete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "update board "
					+ "set parent_no=-1, depths=0 "
					+ "where parent_no=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error " + e);
			}
		}
		return result;
	}
}
