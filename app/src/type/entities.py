from sqlalchemy.orm import declarative_base
from sqlalchemy import Column, ForeignKey, Enum as SQLEnum
from sqlalchemy import Integer, BigInteger, String, Date, DateTime, Boolean

from type.types import Job, Gender

from datetime import datetime

Base = declarative_base()

class Member(Base):
    __tablename__ = "member"
    
    id = Column(BigInteger, primary_key=True, autoincrement=True)
    # sub = Column(String(255), unique=True)
    # nickname = Column(String(20))
    gender = Column(SQLEnum(Gender), nullable=True)
    birth = Column(Date, nullable=True)
    job = Column(SQLEnum(Job), nullable=True)
    # provider = Column(SQLEnum('GOOGLE', 'KAKAO'), nullable=False)
    # role = Column(SQLEnum('ROLE_USER', 'ROLE_ADMIN'), default='ROLE_USER')
    # created_datetime = Column(DateTime, default=datetime.now)
    # updated_datetime = Column(DateTime, onupdate=datetime.now)
    # point = Column(Integer(17), default=0, unsigned=True)
    # current_streak_color_id = Column(Integer(5))
    # current_tree_id = Column(Integer(5), ForeignKey('tree.id'), default=1)
    # current_tree_color_id = Column(Integer(5), default=11)
    # last_harvested_date = Column(Date)
    # paid_recovery_count = Column(Integer(17), default=0)
    # is_deleted = Column(Boolean, default=False)