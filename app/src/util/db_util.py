# For type hints
from typing import List, Optional
from sqlalchemy.orm.query import Query

from sqlalchemy import create_engine, func, desc
from sqlalchemy.orm import sessionmaker

from dotenv import load_dotenv
import os 
from type.entities import Member


class DBUtil():
    load_dotenv()
    __host = os.getenv("MYSQL_HOST")
    __port = int(os.getenv("MYSQL_PORT"))
    __username = os.getenv("MYSQL_USERNAME")
    __password = os.getenv("MYSQL_PASSWORD")
    __database = os.getenv("MYSQL_DATABASE")

    Session = sessionmaker(
        bind=create_engine(
            f"mysql+mysqldb://{__username}:{__password}@{__host}:{__port}/{__database}"
        )
    )


    @classmethod
    def find_member_by_id(cls, member_id: int) -> Optional[Member]:
        with cls.Session() as session:
            query: Query[Member] = session.query(Member).filter_by(id=member_id)

            return query.first()
            